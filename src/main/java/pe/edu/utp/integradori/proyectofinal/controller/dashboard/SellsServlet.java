package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import com.google.common.collect.ImmutableList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.RegexValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.integradori.proyectofinal.dao.FarmacoDAOImpl;
import pe.edu.utp.integradori.proyectofinal.dao.VentaDAOImpl;
import pe.edu.utp.integradori.proyectofinal.handler.EmailHandler;
import pe.edu.utp.integradori.proyectofinal.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet(name = "sellsServlet", value = "/dashboard/sells")
public class SellsServlet extends HttpServlet {

    public static Logger logger = LoggerFactory.getLogger(SellsServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        VentaDAOImpl ventaDAO = new VentaDAOImpl();
        List<Venta> ventas = ventaDAO.readAll();

        FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();
        List<Farmaco> productos = farmacoDAO.readAll();
        productos.removeIf(farmaco -> farmaco.getStock() == 0);

        Trabajador trabajador = (Trabajador) request.getSession().getAttribute("usuario");
        if(!trabajador.getRoles().contains(Rol.Supervisor))
            // Si el usuario no es supervisor entonces solo se muestran las ventas que pertenecen al usuario
            ventas.removeIf(venta -> venta.getVendedor().getId() != trabajador.getId());

        ventas.sort(Comparator.comparing(Venta::getId).reversed());

        request.setAttribute("ventasCargadas", ventas);
        request.setAttribute("productosCargados", productos);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/sells.jsp");
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parametersMap = request.getParameterMap();
        String dniComprador = parametersMap.get("dnicomprador")[0];

        RegexValidator dniValidator = new RegexValidator("^\\d{8}$");

        if (!dniValidator.isValid(dniComprador)) {
            request.getSession().setAttribute("errorDNI", "<div class=\"alert alert-danger\">\n" +
                    "  <strong>El DNI ingresado no es válido. Debe tener 8 dígitos.</strong>\n" +
                    "</div>");
            logger.error("El trabajador " + ((Trabajador) request.getSession().getAttribute("usuario")).getId() + " ha intentado ingresar un DNI no válido al registrar una venta");
            response.sendRedirect(request.getContextPath() + "/dashboard/sells");
            return;
        }

        Venta venta = new Venta(
                1,
                (Trabajador) request.getSession().getAttribute("usuario"),
                new ArrayList<>(), // Aquí se añadirán los detalles
                LocalDateTime.now(),
                dniComprador
        );

        List<DetalleVenta> detalles = new ArrayList<>();

        Farmaco farmaco = null;
        byte cantidad = 0;
        float precio = 0.0f;

        FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();

        Map<Integer, Integer> farmacoCantidadMap = new HashMap<>();

        for (Map.Entry<String, String[]> entry : parametersMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();

            if (paramName.startsWith("idp")) {
                int index = Integer.parseInt(paramName.substring(3));
                int idFarmaco = Integer.parseInt(paramValues[0]);
                farmacoCantidadMap.put(index, idFarmaco);
            }
        }

        for (Map.Entry<String, String[]> entry : parametersMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();

            if (paramName.startsWith("cantidadp")) {
                int index = Integer.parseInt(paramName.substring(9));
                cantidad = Byte.parseByte(paramValues[0]);

                // Aseguramos que el fármaco correspondiente esté presente
                int idFarmaco = farmacoCantidadMap.getOrDefault(index, -1);
                if (idFarmaco == -1) {
                    logger.error("No se encontró un fármaco para el índice " + index);
                    request.getSession().setAttribute("error", "No se encontró un fármaco asociado para el índice " + index);
                    response.sendRedirect(request.getContextPath() + "/dashboard/sells");
                    return;
                }

                // Intentamos obtener el fármaco de la base de datos
                try {
                    farmaco = farmacoDAO.read(idFarmaco);
                    if (farmaco == null) {
                        throw new IllegalStateException("Fármaco no encontrado en la base de datos para el ID: " + idFarmaco);
                    }
                } catch (SQLException e) {
                    logger.error("Error al leer el fármaco con ID: " + idFarmaco, e);
                    request.getSession().setAttribute("error", "No se pudo procesar el fármaco con ID: " + idFarmaco);
                    response.sendRedirect(request.getContextPath() + "/dashboard/sells");
                    return;
                }

                precio = farmaco.getPrecio();
                DetalleVenta detalle = new DetalleVenta(farmaco, cantidad, precio);
                detalles.add(detalle);
            }
        }

        venta.setDetalles(detalles);

        VentaDAOImpl ventaDAO = new VentaDAOImpl();
        try {
            ventaDAO.create(venta);
        } catch (SQLException e) {
            request.setAttribute("exception", e.getMessage());
            request.getRequestDispatcher("/error/error500.jsp").forward(request, response);
            return;
        }

        logger.info("El trabajador " + ((Trabajador) request.getSession().getAttribute("usuario")).getId() + " ha registrado una venta");

        Trabajador trabajador = (Trabajador) request.getSession().getAttribute("usuario");
        StringBuilder correoBody = new StringBuilder();
        correoBody.append("Hola, ").append(trabajador.getNombres()).append(",\n\n")
                .append("Se ha registrado correctamente una venta a nombre del comprador con DNI: ").append(dniComprador).append(".\n")
                .append("Detalles de la venta:\n");

        float totalVenta = 0.0f;
        for (DetalleVenta detalle : detalles) {
            correoBody.append("- Producto: ").append(detalle.getFarmaco().getNombre())
                    .append(", Cantidad: ").append(detalle.getCantidad())
                    .append(", Precio Unitario: S/").append(detalle.getPrecio_unidad())
                    .append("\n");
            totalVenta += detalle.getCantidad() * detalle.getPrecio_unidad();
        }

        correoBody.append("\nTotal de la venta: S/").append(totalVenta).append("\n\n")
                .append("Gracias por usar nuestro sistema.\n")
                .append("Atentamente,\nEl equipo de Líder Médica");

        EmailHandler.sendEmail(
                trabajador.getCorreo(),
                "Confirmación de nueva venta",
                correoBody.toString()
        );

        response.sendRedirect(request.getContextPath() + "/dashboard/sells");
    }

}

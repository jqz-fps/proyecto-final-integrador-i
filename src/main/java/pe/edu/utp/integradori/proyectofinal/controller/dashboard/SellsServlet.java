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
import pe.edu.utp.integradori.proyectofinal.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

        for (Map.Entry<String, String[]> entry : parametersMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();

            if (paramName.startsWith("idp")) {
                int index = Integer.parseInt(paramName.substring(3));
                int idFarmaco = Integer.parseInt(paramValues[0]);

                try {
                    farmaco = farmacoDAO.read(idFarmaco);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else if (paramName.startsWith("cantidadp")) {
                int index = Integer.parseInt(paramName.substring(9));
                cantidad = Byte.parseByte(paramValues[0]);

                assert farmaco != null;
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
        response.sendRedirect(request.getContextPath() + "/dashboard/sells");
    }



}

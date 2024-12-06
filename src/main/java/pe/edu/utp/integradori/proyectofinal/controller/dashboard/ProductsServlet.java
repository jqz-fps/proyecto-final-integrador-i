package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import com.google.common.collect.ImmutableList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.integradori.proyectofinal.dao.CategoriaDAOImpl;
import pe.edu.utp.integradori.proyectofinal.dao.FarmacoDAOImpl;
import pe.edu.utp.integradori.proyectofinal.dao.LaboratorioDAOImpl;
import pe.edu.utp.integradori.proyectofinal.handler.StringUtilities;
import pe.edu.utp.integradori.proyectofinal.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "productsServlet", value = "/dashboard/products")
public class ProductsServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(ProductsServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();
        List<Farmaco> productos = farmacoDAO.readAll();

        LaboratorioDAOImpl laboratorioDAO = new LaboratorioDAOImpl();
        List<Laboratorio> laboratorios = laboratorioDAO.readAll();

        CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();
        List<Categoria> categorias = categoriaDAO.readAll();

        request.setAttribute("productosCargados", productos);
        request.setAttribute("laboratoriosCargados", laboratorios);
        request.setAttribute("categoriasCargadas", categorias);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/products.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = StringUtilities.sanitize(request.getParameter("nombre"));
        String presentacion = StringUtilities.sanitize(request.getParameter("presentacion"));
        String composicion = StringUtilities.sanitize(request.getParameter("composicion"));
        String precioParam = StringUtilities.sanitize(request.getParameter("precio"));
        String stockParam = StringUtilities.sanitize(request.getParameter("stock"));
        String laboratorioIdParam = StringUtilities.sanitize(request.getParameter("laboratorio"));
        String[] categoriasIds = request.getParameterValues("categoria");
        if (nombre == null || presentacion == null || composicion == null ||
                precioParam == null || stockParam == null || laboratorioIdParam == null || categoriasIds == null) {
            request.setAttribute("error", "Todos los campos son obligatorios.");
            response.sendRedirect(request.getContextPath() + "/dashboard/products");
            return;
        }
        try {
            float precio = Float.parseFloat(precioParam);
            int stock = Integer.parseInt(stockParam);
            int laboratorioId = Integer.parseInt(laboratorioIdParam);
            LaboratorioDAOImpl laboratorioDAO = new LaboratorioDAOImpl();
            Laboratorio laboratorio = laboratorioDAO.read(laboratorioId);
            if (laboratorio == null) {
                request.setAttribute("error", "Laboratorio no encontrado.");
                response.sendRedirect(request.getContextPath() + "/dashboard/products");
                return;
            }

            CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();
            List<Categoria> categorias = new ArrayList<>();
            for (String categoriaIdStr : categoriasIds) {
                int categoriaId = Integer.parseInt(categoriaIdStr);
                Categoria categoria = categoriaDAO.read(categoriaId);
                if (categoria != null) {
                    categorias.add(categoria);
                }
            }

            Farmaco farmaco = new Farmaco(
                    1,
                    laboratorio,
                    categorias,
                    null,
                    stock,
                    nombre,
                    "",
                    presentacion,
                    composicion,
                    precio
            );

            FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();
            farmacoDAO.create(farmaco);

            request.getSession().setAttribute("success", "Producto agregado correctamente.");
            response.sendRedirect(request.getContextPath() + "/dashboard/products");
            logger.info("El usuario " + ((Trabajador) request.getSession().getAttribute("usuario")).getId() + " ha agregado un producto");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "El precio y el stock deben ser valores numéricos.");
            response.sendRedirect(request.getContextPath() + "/dashboard/products");
            logger.error("El usuario " + ((Trabajador) request.getSession().getAttribute("usuario")).getId() + " ha intentado agregar un producto con un precio o stock no numérico");
        } catch (Exception e) {
            request.setAttribute("error", "Ocurrió un error al guardar el producto: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/dashboard/products");
            logger.error("El usuario " + ((Trabajador) request.getSession().getAttribute("usuario")).getId() + " ha intentado agregar un producto con un error al guardar el producto: " + e.getMessage());
        }
    }

}

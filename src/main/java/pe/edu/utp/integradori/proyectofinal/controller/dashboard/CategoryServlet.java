package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.integradori.proyectofinal.dao.CategoriaDAOImpl;
import pe.edu.utp.integradori.proyectofinal.model.Categoria;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoriaServlet", value = "/dashboard/categories")
public class CategoryServlet extends HttpServlet {

    private CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        List<Categoria> categorias = categoriaDAO.readAll();
        request.setAttribute("categoriasCargadas", categorias);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/category.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                String nombre = request.getParameter("nombre");
                Categoria nuevaCategoria = new Categoria(0, nombre);
                categoriaDAO.create(nuevaCategoria);
                response.sendRedirect(request.getContextPath() + "/dashboard/categories");

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                Categoria categoriaActualizada = new Categoria(id, nombre);
                categoriaDAO.update(categoriaActualizada);
                response.sendRedirect(request.getContextPath() + "/dashboard/categories");

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                categoriaDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/dashboard/categories");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al procesar la operación.");
            doGet(request, response);
        }
    }
}

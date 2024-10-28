package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.integradori.proyectofinal.dao.DistribuidoraDAOImpl;
import pe.edu.utp.integradori.proyectofinal.model.Distribuidora;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DistributorsServlet", value = "/dashboard/distributors")
public class DistributorsServlet extends HttpServlet {

    private DistribuidoraDAOImpl distribuidoraDAO = new DistribuidoraDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        List<Distribuidora> distribuidora = distribuidoraDAO.readAll();
        request.setAttribute("distribuidorasCargadas", distribuidora);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/distributors.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                String nombre = request.getParameter("nombre");
                String alias = request.getParameter("alias");
                String email = request.getParameter("email");
                String telefono = request.getParameter("telefono");

                Distribuidora nuevaDistribuidora = new Distribuidora(0, nombre, alias, email, telefono);
                distribuidoraDAO.create(nuevaDistribuidora);
                response.sendRedirect(request.getContextPath() + "/dashboard/distributors");

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String alias = request.getParameter("alias");
                String email = request.getParameter("email");
                String telefono = request.getParameter("telefono");

                Distribuidora distribuidoraActualizada = new Distribuidora(id, nombre, alias, email, telefono);
                distribuidoraDAO.update(distribuidoraActualizada);
                response.sendRedirect(request.getContextPath() + "/dashboard/distributors");

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                distribuidoraDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/dashboard/distributors");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al procesar la operación.");
            doGet(request, response);
        }
    }
}

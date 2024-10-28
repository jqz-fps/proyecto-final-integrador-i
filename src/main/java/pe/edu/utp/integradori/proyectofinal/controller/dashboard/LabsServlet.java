package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.integradori.proyectofinal.dao.CategoriaDAOImpl;
import pe.edu.utp.integradori.proyectofinal.dao.FarmacoDAOImpl;
import pe.edu.utp.integradori.proyectofinal.dao.LaboratorioDAOImpl;
import pe.edu.utp.integradori.proyectofinal.model.Categoria;
import pe.edu.utp.integradori.proyectofinal.model.Farmaco;
import pe.edu.utp.integradori.proyectofinal.model.Laboratorio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LabsServlet", value = "/dashboard/labs")
public class LabsServlet extends HttpServlet {

    private LaboratorioDAOImpl laboratorioDAO = new LaboratorioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        List<Laboratorio> laboratorios = laboratorioDAO.readAll();
        request.setAttribute("laboratoriosCargados", laboratorios);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/labs.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                String nombre = request.getParameter("nombre");
                Laboratorio nuevoLaboratorio = new Laboratorio(0, nombre);
                laboratorioDAO.create(nuevoLaboratorio);
                response.sendRedirect(request.getContextPath() + "/dashboard/labs");

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                Laboratorio laboratorioActualizado = new Laboratorio(id, nombre);
                laboratorioDAO.update(laboratorioActualizado);
                response.sendRedirect(request.getContextPath() + "/dashboard/labs");

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                laboratorioDAO.delete(id);
                response.sendRedirect(request.getContextPath() + "/dashboard/labs");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al procesar la operación.");
            doGet(request, response);
        }
    }
}


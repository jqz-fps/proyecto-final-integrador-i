package pe.edu.utp.integradori.proyectofinal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    public static Logger logger = org.slf4j.LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info("El usuario " + ((Trabajador) session.getAttribute("usuario")).getId() + " ha cerrado sesión");
            session.invalidate();
        }

        response.sendRedirect(request.getContextPath() + "/login");
    }
}

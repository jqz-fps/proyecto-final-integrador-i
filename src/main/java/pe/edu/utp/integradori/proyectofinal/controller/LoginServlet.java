package pe.edu.utp.integradori.proyectofinal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.edu.utp.integradori.proyectofinal.dao.TrabajadorDAOImpl;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    public static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            logger.info("El trabajador " + ((Trabajador) session.getAttribute("usuario")).getId() + " ha ingresado al sistema");
            response.sendRedirect(request.getContextPath() + "/dashboard/products");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        if(request.getParameter("username") != null && request.getParameter("password") != null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            request.getSession().removeAttribute("error");

            TrabajadorDAOImpl dao = new TrabajadorDAOImpl();
            Trabajador trabajador = dao.readLogin(username, password);

            if (trabajador != null) {
                String dni = trabajador.getDni();

                if (password.equals(dni)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", trabajador);
                    request.getRequestDispatcher("/cambiar.jsp").forward(request, response);
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", trabajador);
                    logger.info("El trabajador " + trabajador.getId() + " ha iniciado sesión");
                    response.sendRedirect(request.getContextPath() + "/dashboard/products");
                }
            } else {
                request.setAttribute("error", "<div class=\"alert alert-danger mt-3\">Usuario o contraseña incorrectos</div>");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } else if (request.getParameter("passworda") != null && request.getParameter("password") != null) {
            String password = request.getParameter("passworda");
            TrabajadorDAOImpl dao = new TrabajadorDAOImpl();
            Trabajador trabajador = (Trabajador) request.getSession().getAttribute("usuario");
            dao.updatePassword(trabajador, password);
            logger.info("El trabajador " + trabajador.getId() + " ha actualizado su contraseña");
            response.sendRedirect(request.getContextPath() + "/dashboard/products");
        }
    }


}

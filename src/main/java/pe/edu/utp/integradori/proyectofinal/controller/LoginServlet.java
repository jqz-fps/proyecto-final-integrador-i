package pe.edu.utp.integradori.proyectofinal.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.edu.utp.integradori.proyectofinal.model.Rol;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;
import pe.edu.utp.integradori.proyectofinal.model.Venta;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard/products");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        request.getSession().removeAttribute("error");

        if ("admin".equals(username) && "admin".equals(password)) {
            ArrayList<Rol> roles = new ArrayList<>();
            roles.add(Rol.Supervisor);
            roles.add(Rol.Vendedor);
            Trabajador trabajador = new Trabajador(123, "123445678",
                    "Admin", "AdminPat", "AdminMat", 'M', LocalDateTime.now(), LocalDateTime.now(),
                    roles, new ArrayList<Venta>());

            HttpSession session = request.getSession();
            session.setAttribute("usuario", trabajador);

            response.sendRedirect(request.getContextPath() + "/dashboard/products");
        } else {
            request.setAttribute("error", "<div class=\"alert alert-danger mt-3\">Usuario o contraseña incorrectos</div>");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

}

package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import com.google.common.collect.ImmutableList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.integradori.proyectofinal.dao.VentaDAOImpl;
import pe.edu.utp.integradori.proyectofinal.model.*;

import java.io.IOException;

@WebServlet(name = "sellDetailsServlet", value = "/dashboard/sells/details")
public class SellDetailsServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        int idVenta = Integer.parseInt(request.getParameter("idVenta"));
        VentaDAOImpl ventaDAO = new VentaDAOImpl();
        Venta venta = ventaDAO.read(idVenta);
        Trabajador trabajador = (Trabajador) request.getSession().getAttribute("usuario");
        if(trabajador.getId() != venta.getVendedor().getId()){
            response.sendRedirect(request.getContextPath() + "/dashboard/sells");
            return;
        }
        request.setAttribute("venta", venta);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/selldetails.jsp");
        rd.forward(request, response);
    }
}

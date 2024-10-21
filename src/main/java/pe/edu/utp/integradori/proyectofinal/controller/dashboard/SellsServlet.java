package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import com.google.common.collect.ImmutableList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.integradori.proyectofinal.dao.VentaDAOImpl;
import pe.edu.utp.integradori.proyectofinal.model.Rol;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;
import pe.edu.utp.integradori.proyectofinal.model.Venta;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "sellsServlet", value = "/dashboard/sells")
public class SellsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        VentaDAOImpl ventaDAO = new VentaDAOImpl();
        List<Venta> ventas = ventaDAO.readAll();

        Trabajador trabajador = (Trabajador) request.getSession().getAttribute("usuario");
        if(!trabajador.getRoles().contains(Rol.Supervisor))
            // Si el usuario no es supervisor entonces solo se muestran las ventas que pertenecen al usuario
            ventas.removeIf(venta -> venta.getVendedor().getId() != trabajador.getId());

        request.setAttribute("ventasCargadas", ventas);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/sells.jsp");
        rd.forward(request, response);
    }

}

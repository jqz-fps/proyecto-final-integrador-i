package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.integradori.proyectofinal.model.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebServlet(name = "sellDetailsServlet", value = "/dashboard/sells/details")
public class SellDetailsServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        int idVenta = Integer.parseInt(request.getParameter("idVenta"));
        // Aqui se va a tener que buscar la venta
        Trabajador trabajador = new Trabajador(123, "123445678",
                "Juan", "Alberto", "AdminMat", 'M', LocalDateTime.now(), LocalDateTime.now(),
                new ArrayList<Rol>(), new ArrayList<Venta>());
        ArrayList<DetalleVenta> detallesVenta = new ArrayList<>();
        detallesVenta.add(new DetalleVenta(new Farmaco(1, null, null, null, 50,
                "Amoxicilina", "500 mg", "Amoxicilina", "Cápsula",
                49.99f), (byte) 2, 2.99F));
        detallesVenta.add(new DetalleVenta(new Farmaco(4, null, null, null, 50,
                "Ibuprofeno", "500 mg", "Ibuprofeno", "Cápsula",
                49.99f), (byte) 2, 2.99F));
        detallesVenta.add(new DetalleVenta(new Farmaco(2, null, null, null, 50,
                "Aspirina", "500 mg", "Aspirina", "Cápsula",
                49.99f), (byte) 2, 2.99F));
        Venta venta = new Venta(1, trabajador, detallesVenta, LocalDateTime.now(), "72506993");
        request.setAttribute("venta", venta);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/selldetails.jsp");
        rd.forward(request, response);
    }
}

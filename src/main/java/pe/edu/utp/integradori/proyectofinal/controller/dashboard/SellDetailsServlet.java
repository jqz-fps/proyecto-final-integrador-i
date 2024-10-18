package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import com.google.common.collect.ImmutableList;
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
        Laboratorio laboratorio = new Laboratorio(1, "XYZ");
        Distribuidora distribuidora = new Distribuidora(1, "America SA", "Disfarma", "", "");
        Categoria categoria = new Categoria(1, "Antibiótico");
        ArrayList<DetalleVenta> detallesVentaAL = new ArrayList<>();
        detallesVentaAL.add(new DetalleVenta(new Farmaco(1, laboratorio, categoria, distribuidora, 50,
                "Amoxicilina", "500 mg", "Amoxicilina", "Cápsula",
                49.99f), (byte) 2, 2.99F));
        detallesVentaAL.add(new DetalleVenta(new Farmaco(3, laboratorio, categoria, distribuidora, 50,
                "Amoxicilina", "500 mg", "Amoxicilina", "Cápsula",
                49.99f), (byte) 2, 2.99F));
        detallesVentaAL.add(new DetalleVenta(new Farmaco(2, laboratorio, categoria, distribuidora, 50,
                "Amoxicilina", "500 mg", "Amoxicilina", "Cápsula",
                49.99f), (byte) 2, 2.99F));
        ImmutableList<DetalleVenta> detallesVenta = ImmutableList.copyOf(detallesVentaAL);
        Venta venta = new Venta(1, trabajador, detallesVenta, LocalDateTime.now(), "72506993");
        request.setAttribute("venta", venta);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/selldetails.jsp");
        rd.forward(request, response);
    }
}

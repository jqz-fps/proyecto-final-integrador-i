package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import com.google.common.collect.ImmutableList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.integradori.proyectofinal.model.DetalleVenta;
import pe.edu.utp.integradori.proyectofinal.model.Rol;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;
import pe.edu.utp.integradori.proyectofinal.model.Venta;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebServlet(name = "sellsServlet", value = "/dashboard/sells")
public class SellsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        Trabajador trabajador = new Trabajador(123, "123445678",
                "Juan", "Alberto", "AdminMat", 'M', LocalDateTime.now(), LocalDateTime.now(),
                new ArrayList<Rol>(), new ArrayList<Venta>());
        ArrayList<Venta> ventasAL = new ArrayList();
        ventasAL.add(new Venta(1, trabajador, new ArrayList<DetalleVenta>(), LocalDateTime.now(), "72506993"));
        ventasAL.add(new Venta(2, trabajador, new ArrayList<DetalleVenta>(), LocalDateTime.now(), "72506993"));
        ventasAL.add(new Venta(3, trabajador, new ArrayList<DetalleVenta>(), LocalDateTime.now(), "72506993"));
        ventasAL.add(new Venta(4, trabajador, new ArrayList<DetalleVenta>(), LocalDateTime.now(), "72506993"));
        ventasAL.add(new Venta(5, trabajador, new ArrayList<DetalleVenta>(), LocalDateTime.now(), "72506993"));
        ventasAL.add(new Venta(6, trabajador, new ArrayList<DetalleVenta>(), LocalDateTime.now(), "72506993"));
        ImmutableList<Venta> ventas = ImmutableList.copyOf(ventasAL);

        request.setAttribute("ventasCargadas", ventas);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/sells.jsp");
        rd.forward(request, response);
    }

}

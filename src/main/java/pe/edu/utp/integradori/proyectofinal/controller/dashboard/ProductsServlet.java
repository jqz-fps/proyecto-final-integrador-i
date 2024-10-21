package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import com.google.common.collect.ImmutableList;
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
import pe.edu.utp.integradori.proyectofinal.model.Distribuidora;
import pe.edu.utp.integradori.proyectofinal.model.Farmaco;
import pe.edu.utp.integradori.proyectofinal.model.Laboratorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "productsServlet", value = "/dashboard/products")
public class ProductsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();
        List<Farmaco> productos = farmacoDAO.readAll();

        LaboratorioDAOImpl laboratorioDAO = new LaboratorioDAOImpl();
        List<Laboratorio> laboratorios = laboratorioDAO.readAll();

        CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();
        List<Categoria> categorias = categoriaDAO.readAll();

        request.setAttribute("productosCargados", productos);
        request.setAttribute("laboratoriosCargados", laboratorios);
        request.setAttribute("categoriasCargadas", categorias);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/products.jsp");
        rd.forward(request, response);
    }
}

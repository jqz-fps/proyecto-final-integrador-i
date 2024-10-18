package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.integradori.proyectofinal.model.Categoria;
import pe.edu.utp.integradori.proyectofinal.model.Distribuidora;
import pe.edu.utp.integradori.proyectofinal.model.Farmaco;
import pe.edu.utp.integradori.proyectofinal.model.Laboratorio;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "productsServlet", value = "/dashboard/products")
public class ProductsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        ArrayList<Laboratorio> laboratorios = new ArrayList<>();
        Laboratorio laboratorio1 = new Laboratorio(1, "XYZ");
        Laboratorio laboratorio2 = new Laboratorio(2, "ZYX");
        laboratorios.add(laboratorio1);
        laboratorios.add(laboratorio2);

        ArrayList<Distribuidora> distribuidoras = new ArrayList<>();
        Distribuidora distribuidora1 = new Distribuidora(1, "America SA", "Disfarma", "", "");
        Distribuidora distribuidora2 = new Distribuidora(2, "Biomedic SAC", "Biomedic", "", "");
        distribuidoras.add(distribuidora1);
        distribuidoras.add(distribuidora2);

        ArrayList<Categoria> categorias = new ArrayList<>();
        Categoria categoria1 = new Categoria(1, "Antibiótico");
        Categoria categoria2 = new Categoria(2, "Analgésico");
        Categoria categoria3 = new Categoria(3, "Antiinflamatorio");
        Categoria categoria4 = new Categoria(4, "Inmunosupresor");
        Categoria categoria5 = new Categoria(5, "Antidepresivo");
        categorias.add(categoria1);
        categorias.add(categoria2);
        categorias.add(categoria3);
        categorias.add(categoria4);
        categorias.add(categoria5);

        ArrayList<Farmaco> productos = new ArrayList<>();
        productos.add(new Farmaco(1, laboratorio1, categoria1, distribuidora1, 50, "Amoxicilina", "500 mg", "Amoxicilina", "Cápsula", 49.99f));
        productos.add(new Farmaco(2, laboratorio2, categoria2, distribuidora2, 100, "Aspirina", "100 mg", "Ácido Acetilsalicílico", "Tableta", 9.99f));
        productos.add(new Farmaco(3, laboratorio2, categoria1, distribuidora1, 30, "Zyrtec", "10 mg", "Cetirizina", "Comprimido", 29.99f));
        productos.add(new Farmaco(4, laboratorio1, categoria5, distribuidora1, 75, "Enalapril", "20 mg", "Enalapril", "Tableta", 15.99f));
        productos.add(new Farmaco(5, laboratorio2, categoria2, distribuidora2, 20, "Tamiflu", "75 mg", "Oseltamivir", "Cápsula", 149.99f));
        productos.add(new Farmaco(6, laboratorio2, categoria3, distribuidora1, 40, "Mofetil Micofenolato", "500 mg", "Micofenolato", "Tableta", 89.99f));
        productos.add(new Farmaco(7, laboratorio1, categoria3, distribuidora2, 200, "Metformina", "850 mg", "Metformina", "Tableta", 12.99f));
        productos.add(new Farmaco(8, laboratorio1, categoria4, distribuidora2, 60, "Ibuprofeno", "400 mg", "Ibuprofeno", "Comprimido", 19.99f));
        productos.add(new Farmaco(9, laboratorio1, categoria1, distribuidora1, 10, "Valproato de Sodio", "200 mg", "Valproato", "Tableta", 99.99f));
        productos.add(new Farmaco(10, laboratorio1, categoria5, distribuidora1, 35, "Fluoxetina", "20 mg", "Fluoxetina", "Cápsula", 24.99f));

        request.setAttribute("productosCargados", productos);
        request.setAttribute("distribuidorasCargadas", distribuidoras);
        request.setAttribute("laboratoriosCargados", laboratorios);
        request.setAttribute("categoriasCargadas", categorias);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/products.jsp");
        rd.forward(request, response);
    }
}

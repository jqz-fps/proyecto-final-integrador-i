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
        ArrayList<Farmaco> productos = new ArrayList<>();
        productos.add(new Farmaco(1, new Laboratorio(1, "Pfizer"), new Categoria(1, "Antibiótico"), new Distribuidora(1, "Distribuidora1", "Distribuidora1", "contacto1@distribuidora.com", "123456789"), 50, "Amoxicilina", "500 mg", "Amoxicilina", "Cápsula", 49.99f));
        productos.add(new Farmaco(2, new Laboratorio(2, "Bayer"), new Categoria(2, "Analgésico"), new Distribuidora(2, "Distribuidora2", "Distribuidora2", "contacto2@distribuidora.com", "987654321"), 100, "Aspirina", "100 mg", "Ácido Acetilsalicílico", "Tableta", 9.99f));
        productos.add(new Farmaco(3, new Laboratorio(3, "Novartis"), new Categoria(3, "Antihistamínico"), new Distribuidora(3, "Distribuidora3", "Distribuidora3", "contacto3@distribuidora.com", "456789123"), 30, "Zyrtec", "10 mg", "Cetirizina", "Comprimido", 29.99f));
        productos.add(new Farmaco(4, new Laboratorio(4, "Sanofi"), new Categoria(4, "Antihipertensivo"), new Distribuidora(4, "Distribuidora4", "Distribuidora4", "contacto4@distribuidora.com", "321654987"), 75, "Enalapril", "20 mg", "Enalapril", "Tableta", 15.99f));
        productos.add(new Farmaco(5, new Laboratorio(5, "Roche"), new Categoria(5, "Antivirales"), new Distribuidora(5, "Distribuidora5", "Distribuidora5", "contacto5@distribuidora.com", "147258369"), 20, "Tamiflu", "75 mg", "Oseltamivir", "Cápsula", 149.99f));
        productos.add(new Farmaco(6, new Laboratorio(6, "GSK"), new Categoria(6, "Inmunosupresor"), new Distribuidora(6, "Distribuidora6", "Distribuidora6", "contacto6@distribuidora.com", "963852741"), 40, "Mofetil Micofenolato", "500 mg", "Micofenolato", "Tableta", 89.99f));
        productos.add(new Farmaco(7, new Laboratorio(7, "AbbVie"), new Categoria(7, "Antidiabético"), new Distribuidora(7, "Distribuidora7", "Distribuidora7", "contacto7@distribuidora.com", "789456123"), 200, "Metformina", "850 mg", "Metformina", "Tableta", 12.99f));
        productos.add(new Farmaco(8, new Laboratorio(8, "Merck"), new Categoria(8, "Antiinflamatorio"), new Distribuidora(8, "Distribuidora8", "Distribuidora8", "contacto8@distribuidora.com", "258369147"), 60, "Ibuprofeno", "400 mg", "Ibuprofeno", "Comprimido", 19.99f));
        productos.add(new Farmaco(9, new Laboratorio(9, "AstraZeneca"), new Categoria(9, "Anticonvulsivante"), new Distribuidora(9, "Distribuidora9", "Distribuidora9", "contacto9@distribuidora.com", "654789321"), 10, "Valproato de Sodio", "200 mg", "Valproato", "Tableta", 99.99f));
        productos.add(new Farmaco(10, new Laboratorio(10, "Lilly"), new Categoria(10, "Antidepresivo"), new Distribuidora(10, "Distribuidora10", "Distribuidora10", "contacto10@distribuidora.com", "852963741"), 35, "Fluoxetina", "20 mg", "Fluoxetina", "Cápsula", 24.99f));
        request.setAttribute("productosCargados", productos);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/products.jsp");
        rd.forward(request, response);
    }
}

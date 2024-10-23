package pe.edu.utp.integradori.proyectofinal.controller.dashboard.supervisors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.RegexValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.integradori.proyectofinal.dao.TrabajadorDAOImpl;
import pe.edu.utp.integradori.proyectofinal.handler.EmailHandler;
import pe.edu.utp.integradori.proyectofinal.model.Rol;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "supWorkersServlet", value = "/dashboard/supervision/workers")
public class WorkersServlet extends HttpServlet {

    public static Logger logger = LoggerFactory.getLogger(WorkersServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        TrabajadorDAOImpl trabajadorDAO = new TrabajadorDAOImpl();

        Trabajador trabajadorlog = (Trabajador) request.getSession().getAttribute("usuario");
        if(!trabajadorlog.hasRole(Rol.Supervisor)) {
            response.sendRedirect(request.getContextPath() + "/dashboard/sells");
            return;
        }

        List<Trabajador> trabajadores = trabajadorDAO.readAll();
        trabajadores.removeIf(trabajador -> trabajadorlog.getId() == trabajador.getId());

        trabajadores.sort(Comparator.comparing(Trabajador::getId).reversed());

        request.setAttribute("trabajadoresCargados", trabajadores);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard/supervisors/workers.jsp");
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dni = request.getParameter("dni");
        String dniedit = request.getParameter("edit-dni");

        if(dni != null) {
            String nombres = request.getParameter("nombres");
            String ap_paterno = request.getParameter("apellido_paterno");
            String ap_materno = request.getParameter("apellido_materno");
            String sexo = request.getParameter("genero");
            LocalDate fecha_nacimiento = LocalDate.parse(request.getParameter("fecha_nacimiento"));
            LocalDateTime fecha_registro = LocalDateTime.now();
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");
            List<Rol> roles = new ArrayList<>();
            if (request.getParameter("supervisor") != null) roles.add(Rol.Supervisor);

            RegexValidator dniValidator = new RegexValidator("^\\d{8}$");
            if (!dniValidator.isValid(dni)) {
                request.getSession().setAttribute("errorDNI", "<div class=\"alert alert-danger\">\n" +
                        "  <strong>El DNI ingresado no es válido. Debe tener 8 dígitos.</strong>\n" +
                        "</div>");
                logger.error("El supervisor " + (Trabajador) request.getSession().getAttribute("usuario") + " ha ingresado un DNI no válido al agregar un trabajador");
                response.sendRedirect(request.getContextPath() + "/dashboard/supervision/workers");
                return;
            }

            RegexValidator correoValidator = new RegexValidator("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
            if (!correoValidator.isValid(correo)) {
                request.getSession().setAttribute("errorCorreo", "<div class=\"alert alert-danger\">\n" +
                        "  <strong>El correo ingresado no es válido.</strong>\n" +
                        "</div>");
                logger.error("El supervisor " + (Trabajador) request.getSession().getAttribute("usuario") + " ha ingresado un correo no válido al agregar un trabajador");
                response.sendRedirect(request.getContextPath() + "/dashboard/supervision/workers");
                return;
            }

            if (!fecha_nacimiento.isBefore(LocalDate.now().minusYears(18))) {
                request.getSession().setAttribute("errorFechaNacimiento", "<div class=\"alert alert-danger\">\n" +
                        "  <strong>La fecha de nacimiento ingresada no es válida.</strong>\n" +
                        "</div>");
                logger.error("El supervisor " + (Trabajador) request.getSession().getAttribute("usuario") + " ha ingresado una fecha de nacimiento no válida al agregar un trabajador");
                response.sendRedirect(request.getContextPath() + "/dashboard/supervision/workers");
                return;
            }

            RegexValidator telefonoValidator = new RegexValidator("^\\d{1,15}$");
            if (!telefonoValidator.isValid(telefono)) {
                request.getSession().setAttribute("errorTelefono", "<div class=\"alert alert-danger\">\n" +
                        "  <strong>El telefono ingresado no es válido.</strong>\n" +
                        "</div>");
                logger.error("El supervisor " + (Trabajador) request.getSession().getAttribute("usuario") + " ha ingresado un telefono no válido al agregar un trabajador");
                response.sendRedirect(request.getContextPath() + "/dashboard/supervision/workers");
                return;
            }

            roles.add(Rol.Vendedor);

            Trabajador trabajador = new Trabajador(
                    1,
                    dni,
                    nombres,
                    ap_paterno,
                    ap_materno,
                    sexo.charAt(0),
                    fecha_nacimiento,
                    fecha_registro,
                    roles,
                    null
            );
            trabajador.setCorreo(correo);
            trabajador.setTelefono(telefono);
            TrabajadorDAOImpl trabajadorDAO = new TrabajadorDAOImpl();
            try {
                trabajadorDAO.create(trabajador);
            } catch (SQLException e) {
                request.setAttribute("exception", e.getMessage());
                request.getRequestDispatcher("/error/error500.jsp").forward(request, response);
                return;
            }

            EmailHandler.sendEmail(trabajador.getCorreo(),
                    "Bienvenido a Líder Médica",
                    "Estimado/a " + trabajador.getNombres() + " " + trabajador.getAp_paterno() + " " + trabajador.getAp_materno() + ",\n\n" +
                            "¡Bienvenido/a a nuestra plataforma! Nos complace informarte que tu registro ha sido exitoso.\n\n" +
                            "Aquí están los detalles de tu cuenta:\n" +
                            "- **Correo electrónico:** " + trabajador.getCorreo() + "\n" +
                            "- **Contraseña inicial:** " + trabajador.getDni() + "\n" +
                            "*Para hacer uso del sistema es necesario cambiar la contraseña al iniciar sesión.*\n" +
                            "Si tienes alguna pregunta o necesitas asistencia, no dudes en contactarnos.\n\n" +
                            "Atentamente,\n" +
                            "El equipo de Líder Médica"
            );

            request.getSession().setAttribute("finalAgregado", "<div class=\"alert alert-success\">\n" +
                    "  <strong>Se ha agregado al trabajador.</strong>\n" +
                    "</div>");

            logger.info("El supervisor " + (Trabajador) request.getSession().getAttribute("usuario") + " ha agregado al trabajador " + trabajador.getId());

            response.sendRedirect(request.getContextPath() + "/dashboard/supervision/workers");
        } else if (dniedit != null) {
            try {
            TrabajadorDAOImpl trabajadorDAO = new TrabajadorDAOImpl();
            String idedit = request.getParameter("edit-id");

            Trabajador trabajador = trabajadorDAO.read(Integer.parseInt(idedit));

            trabajador.setDni(dniedit);
            trabajador.setNombres(request.getParameter("edit-nombres"));
            trabajador.setAp_paterno(request.getParameter("edit-apellido_paterno"));
            trabajador.setAp_materno(request.getParameter("edit-apellido_materno"));
            trabajador.setGenero(request.getParameter("edit-genero").charAt(0));
            trabajador.setFecha_nacimiento(LocalDate.parse(request.getParameter("edit-fecha_nacimiento")));
            trabajador.setCorreo(request.getParameter("edit-correo"));
            trabajador.setTelefono(request.getParameter("edit-telefono"));
            trabajador.setRoles(new ArrayList<>());
            if (request.getParameter("edit-supervisor") != null) {
                if (!trabajador.getRoles().contains(Rol.Supervisor)) {
                    trabajador.getRoles().add(Rol.Supervisor);
                }
            } else {
                trabajador.getRoles().remove(Rol.Supervisor);
            }

            request.getSession().setAttribute("finalAgregado", "<div class=\"alert alert-success\">\n" +
                    "  <strong>Se ha actualizado al trabajador.</strong>\n" +
                    "</div>");

            logger.info("El supervisor " + (Trabajador) request.getSession().getAttribute("usuario") + " ha actualizado al trabajador " + trabajador.getId());

            response.sendRedirect(request.getContextPath() + "/dashboard/supervision/workers");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

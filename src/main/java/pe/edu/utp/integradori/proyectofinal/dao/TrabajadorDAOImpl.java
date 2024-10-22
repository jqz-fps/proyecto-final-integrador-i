package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.TrabajadorDAO;
import pe.edu.utp.integradori.proyectofinal.model.Rol;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;
import pe.edu.utp.integradori.proyectofinal.model.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDAOImpl extends Conexion implements TrabajadorDAO {
    @Override
    public void create(Trabajador trabajador) throws SQLException {

    }

    @Override
    public Trabajador read(Integer id) throws SQLException {
        String query = "SELECT * FROM trabajador WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PreparedStatement stmt2 = conn.prepareStatement(
                        "SELECT R.nombre FROM rol_trabajador AS RT " +
                                "INNER JOIN rol as R ON RT.id_rol = R.id " +
                                "INNER JOIN trabajador AS T ON RT.id_trabajador = T.id " +
                                "WHERE id_trabajador = ?"
                );
                stmt2.setInt(1, rs.getInt("id"));
                ResultSet rs2 = stmt2.executeQuery();
                ArrayList<Rol> roles = new ArrayList<>();
                while (rs2.next()) {
                    roles.add(Rol.valueOf(rs2.getString("nombre")));
                }

                LocalDate fechaNacimiento = null;
                LocalDate fechaRegistro = null;

                String fechaNacimientoStr = rs.getString("fecha_nacimiento");
                String fechaRegistroStr = rs.getString("fecha_registro");

                if (fechaNacimientoStr != null) {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                }

                if (fechaRegistroStr != null) {
                    fechaRegistro = LocalDate.parse(fechaRegistroStr);
                }

                Trabajador trabajador = new Trabajador(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombres"),
                        rs.getString("ap_paterno"),
                        rs.getString("ap_materno"),
                        rs.getString("genero").charAt(0),
                        fechaNacimiento,
                        fechaRegistro,
                        roles,
                        new ArrayList<>()
                );
                return trabajador;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Trabajador trabajador) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<Trabajador> readAll() {
        String query = "SELECT * FROM trabajador";
        List<Trabajador> trabajadores = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PreparedStatement stmt2 = conn.prepareStatement(
                        "SELECT R.nombre FROM rol_trabajador AS RT " +
                                "INNER JOIN rol as R ON RT.id_rol = R.id " +
                                "INNER JOIN trabajador AS T ON RT.id_trabajador = T.id " +
                                "WHERE id_trabajador = ?"
                );
                stmt2.setInt(1, rs.getInt("id"));
                ResultSet rs2 = stmt2.executeQuery();
                ArrayList<Rol> roles = new ArrayList<>();
                while (rs2.next()) {
                    roles.add(Rol.valueOf(rs2.getString("nombre")));
                }

                LocalDate fechaNacimiento = null;
                LocalDate fechaRegistro = null;

                String fechaNacimientoStr = rs.getString("fecha_nacimiento");
                String fechaRegistroStr = rs.getString("fecha_registro");

                if (fechaNacimientoStr != null) {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                }

                if (fechaRegistroStr != null) {
                    fechaRegistro = LocalDate.parse(fechaRegistroStr);
                }

                Trabajador trabajador = new Trabajador(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombres"),
                        rs.getString("ap_paterno"),
                        rs.getString("ap_materno"),
                        rs.getString("genero").charAt(0),
                        fechaNacimiento,
                        fechaRegistro,
                        roles,
                        new ArrayList<>()
                );
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trabajadores;
    }

    @Override
    public Trabajador readLogin(String usuario, String contrasena) {
        String query = "SELECT * FROM trabajador WHERE correo = ? AND contraseña = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PreparedStatement stmt2 = conn.prepareStatement(
                        "SELECT R.nombre FROM rol_trabajador AS RT " +
                                "INNER JOIN rol as R ON RT.id_rol = R.id " +
                                "INNER JOIN trabajador AS T ON RT.id_trabajador = T.id " +
                                "WHERE id_trabajador = ?"
                );
                stmt2.setInt(1, rs.getInt("id"));
                ResultSet rs2 = stmt2.executeQuery();
                ArrayList<Rol> roles = new ArrayList<>();
                while (rs2.next()) {
                    roles.add(Rol.valueOf(rs2.getString("nombre")));
                }

                LocalDate fechaNacimiento = null;
                LocalDate fechaRegistro = null;

                String fechaNacimientoStr = rs.getString("fecha_nacimiento");
                String fechaRegistroStr = rs.getString("fecha_registro");

                if (fechaNacimientoStr != null) {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                }

                if (fechaRegistroStr != null) {
                    fechaRegistro = LocalDate.parse(fechaRegistroStr);
                }

                Trabajador trabajador = new Trabajador(
                        rs.getInt("id"),
                        rs.getString("dni"),
                        rs.getString("nombres"),
                        rs.getString("ap_paterno"),
                        rs.getString("ap_materno"),
                        rs.getString("genero").charAt(0),
                        fechaNacimiento,
                        fechaRegistro,
                        roles,
                        new ArrayList<>()
                );
                return trabajador;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

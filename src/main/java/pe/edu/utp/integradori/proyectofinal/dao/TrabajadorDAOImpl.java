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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDAOImpl extends Conexion implements TrabajadorDAO {

    @Override
    public void create(Trabajador trabajador) throws SQLException {
        String query = "{ CALL InsertarTrabajador(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, trabajador.getDni());
            stmt.setString(2, trabajador.getNombres());
            stmt.setString(3, trabajador.getAp_paterno());
            stmt.setString(4, trabajador.getAp_materno());
            stmt.setString(5, String.valueOf(trabajador.getGenero()));
            stmt.setString(6, trabajador.getFecha_nacimiento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stmt.setString(7, trabajador.getFecha_registro().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            stmt.setString(8, trabajador.getTelefono());
            stmt.setString(9, trabajador.getCorreo());
            stmt.setString(10, trabajador.getDni());
            int idTrabajador = 0;
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idTrabajador = rs.getInt("id_trabajador");
                }
            }
            trabajador.setId(idTrabajador);

            for (Rol rol : trabajador.getRoles()) {
                String query2 = "INSERT INTO rol_trabajador (id_trabajador, id_rol) VALUES (?, ?)";
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                stmt2.setInt(1, trabajador.getId());
                if(rol.equals(Rol.Supervisor)) {
                    stmt2.setInt(2, 6); // Rol de supervisor
                    stmt2.executeUpdate();
                } else if (rol.equals(Rol.Vendedor)) {
                    stmt2.setInt(2, 7); // Rol de vendedor
                    stmt2.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                LocalDateTime fechaRegistro = null;

                String fechaNacimientoStr = rs.getString("fecha_nacimiento");
                String fechaRegistroStr = rs.getString("fecha_registro");

                if (fechaNacimientoStr != null) {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                }

                if (fechaRegistroStr != null) {
                    fechaRegistro = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(fechaRegistroStr, LocalDateTime::from);
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
                trabajador.setCorreo(rs.getString("correo"));
                trabajador.setTelefono(rs.getString("numero_contacto"));
                return trabajador;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Trabajador trabajador) throws SQLException {
        String query = "UPDATE trabajador SET dni = ?, nombres = ?, ap_paterno = ?, ap_materno = ?, " +
                "genero = ?, fecha_nacimiento = ?, correo = ?, numero_contacto = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, trabajador.getDni());
            stmt.setString(2, trabajador.getNombres());
            stmt.setString(3, trabajador.getAp_paterno());
            stmt.setString(4, trabajador.getAp_materno());
            stmt.setString(5, String.valueOf(trabajador.getGenero()));
            stmt.setString(6, trabajador.getFecha_nacimiento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stmt.setString(7, trabajador.getCorreo());
            stmt.setString(8, trabajador.getTelefono());
            stmt.setInt(9, trabajador.getId());
            stmt.executeUpdate();

            String query3 = "DELETE FROM rol_trabajador WHERE id_trabajador = ?";
            PreparedStatement stmt3 = conn.prepareStatement(query3);
            stmt3.setInt(1, trabajador.getId());
            stmt3.executeUpdate();
            for (Rol rol : trabajador.getRoles()) {
                String query2 = "INSERT INTO rol_trabajador (id_trabajador, id_rol) VALUES (?, ?)";
                PreparedStatement stmt2 = conn.prepareStatement(query2);
                stmt2.setInt(1, trabajador.getId());
                if(rol.equals(Rol.Supervisor)) {
                    stmt2.setInt(2, 6); // Rol de supervisor
                    stmt2.executeUpdate();
                } else if (rol.equals(Rol.Vendedor)) {
                    stmt2.setInt(2, 7); // Rol de vendedor
                    stmt2.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                LocalDateTime fechaRegistro = null;

                String fechaNacimientoStr = rs.getString("fecha_nacimiento");
                String fechaRegistroStr = rs.getString("fecha_registro");

                if (fechaNacimientoStr != null) {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                }

                if (fechaRegistroStr != null) {
                    fechaRegistro = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(fechaRegistroStr, LocalDateTime::from);
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
                trabajador.setCorreo(rs.getString("correo"));
                trabajador.setTelefono(rs.getString("numero_contacto"));
                trabajadores.add(trabajador);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trabajadores;
    }

    @Override
    public Trabajador readLogin(String usuario, String contrasena) {
        String query = "SELECT * FROM trabajador WHERE correo = ? AND contraseña = md5(?)";
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
                LocalDateTime fechaRegistro = null;

                String fechaNacimientoStr = rs.getString("fecha_nacimiento");
                String fechaRegistroStr = rs.getString("fecha_registro");

                if (fechaNacimientoStr != null) {
                    fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
                }

                if (fechaRegistroStr != null) {
                    fechaRegistro = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(fechaRegistroStr, LocalDateTime::from);
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
                trabajador.setCorreo(rs.getString("correo"));
                trabajador.setTelefono(rs.getString("numero_contacto"));
                return trabajador;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updatePassword(Trabajador trabajador, String contrasena) {
        String query = "UPDATE trabajador SET contraseña = md5(?) WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, contrasena);
            stmt.setInt(2, trabajador.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

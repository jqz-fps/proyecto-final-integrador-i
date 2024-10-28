package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.DistribuidoraDAO;
import pe.edu.utp.integradori.proyectofinal.model.Distribuidora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DistribuidoraDAOImpl extends Conexion implements DistribuidoraDAO {

    @Override
    public void create(Distribuidora distribuidora) throws SQLException {
        String query = "INSERT INTO distribuidora (nombre, alias, email, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, distribuidora.getNombre());
            stmt.setString(2, distribuidora.getAlias());
            stmt.setString(3, distribuidora.getEmail());
            stmt.setString(4, distribuidora.getTelefono());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la distribuidora", e);
        }
    }

    @Override
    public Distribuidora read(Integer id_distribuidora) throws SQLException {
        String query = "SELECT id_distribuidora, nombre, alias, email, telefono FROM distribuidora WHERE id_distribuidora = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_distribuidora);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Distribuidora(
                        rs.getInt("id_distribuidora"),
                        rs.getString("nombre"),
                        rs.getString("alias"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer la distribuidora", e);
        }
        return null;
    }

    @Override
    public void update(Distribuidora distribuidora) throws SQLException {
        String query = "UPDATE distribuidora SET nombre = ?, alias = ?, email = ?, telefono = ? WHERE id_distribuidora = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, distribuidora.getNombre());
            stmt.setString(2, distribuidora.getAlias());
            stmt.setString(3, distribuidora.getEmail());
            stmt.setString(4, distribuidora.getTelefono());
            stmt.setInt(5, distribuidora.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la distribuidora", e);
        }
    }

    @Override
    public void delete(Integer id_distribuidora) throws SQLException {
        String query = "DELETE FROM distribuidora WHERE id_distribuidora = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_distribuidora);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la distribuidora", e);
        }
    }

    @Override
    public List<Distribuidora> readAll() {
        List<Distribuidora> distribuidoras = new ArrayList<>();
        String query = "SELECT id_distribuidora, nombre, alias, email, telefono FROM distribuidora";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Distribuidora distribuidora = new Distribuidora(
                        rs.getInt("id_distribuidora"),
                        rs.getString("nombre"),
                        rs.getString("alias"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
                distribuidoras.add(distribuidora);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer todas las distribuidoras", e);
        }
        return distribuidoras;
    }
}

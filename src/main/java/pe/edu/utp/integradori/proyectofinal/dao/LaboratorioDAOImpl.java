package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.LaboratorioDAO;
import pe.edu.utp.integradori.proyectofinal.model.Laboratorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LaboratorioDAOImpl extends Conexion implements LaboratorioDAO {

    @Override
    public void create(Laboratorio laboratorio) throws SQLException {
        String query = "INSERT INTO laboratorio (nombre) VALUES (?)"; // Cambia según tu esquema
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, laboratorio.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el laboratorio", e);
        }
    }

    @Override
    public Laboratorio read(Integer id) throws SQLException {
        String query = "SELECT id, nombre FROM laboratorio WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Laboratorio(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer el laboratorio", e);
        }
        return null;
    }

    @Override
    public void update(Laboratorio laboratorio) throws SQLException {
        String query = "UPDATE laboratorio SET nombre = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, laboratorio.getNombre());
            stmt.setInt(2, laboratorio.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el laboratorio", e);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String query = "DELETE FROM laboratorio WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el laboratorio", e);
        }
    }

    @Override
    public List<Laboratorio> readAll() {
        List<Laboratorio> laboratorios = new ArrayList<>();
        String query = "SELECT id, nombre FROM laboratorio"; // Cambia el nombre de la tabla según tu esquema
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Laboratorio laboratorio = new Laboratorio(rs.getInt("id"), rs.getString("nombre"));
                laboratorios.add(laboratorio);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer todos los laboratorios", e);
        }
        return laboratorios;
    }
}

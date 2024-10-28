package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.CategoriaDAO;
import pe.edu.utp.integradori.proyectofinal.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl extends Conexion implements CategoriaDAO {

    @Override
    public void create(Categoria categoria) throws SQLException {
        String query = "INSERT INTO categoria (nombre) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, categoria.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la categoría", e);
        }
    }

    @Override
    public Categoria read(Integer id) throws SQLException {
        String query = "SELECT id, nombre FROM categoria WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categoria(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer la categoría", e);
        }
        return null;
    }

    @Override
    public void update(Categoria categoria) throws SQLException {
        String query = "UPDATE categoria SET nombre = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(2, categoria.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la categoría", e);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String query = "DELETE FROM categoria WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la categoría", e);
        }
    }

    @Override
    public List<Categoria> readAll() {
        List<Categoria> categorias = new ArrayList<>();
        String query = "SELECT id, nombre FROM categoria";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria(rs.getInt("id"), rs.getString("nombre"));
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer todas las categorías", e);
        }
        return categorias;
    }
}

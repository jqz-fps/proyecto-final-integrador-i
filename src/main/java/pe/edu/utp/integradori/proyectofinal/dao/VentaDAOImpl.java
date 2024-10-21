package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.VentaDAO;
import pe.edu.utp.integradori.proyectofinal.model.Categoria;
import pe.edu.utp.integradori.proyectofinal.model.DetalleVenta;
import pe.edu.utp.integradori.proyectofinal.model.Farmaco;
import pe.edu.utp.integradori.proyectofinal.model.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl extends Conexion implements VentaDAO {
    @Override
    public void create(Venta venta) throws SQLException {

    }

    @Override
    public Venta read(Integer id) {
        String query = "SELECT * FROM venta WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            TrabajadorDAOImpl trabajadorDAO = new TrabajadorDAOImpl();
            while (rs.next()) {
                PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM detalleventa WHERE id_venta = ?");
                stmt2.setInt(1, rs.getInt("id"));
                ResultSet rs2 = stmt2.executeQuery();
                List<DetalleVenta> detalles = new ArrayList<>();
                FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();
                while (rs2.next()) {
                    DetalleVenta detalle = new DetalleVenta(
                            farmacoDAO.read(rs2.getInt("id_farmaco")),
                            rs2.getByte("cantidad"),
                            rs2.getFloat("precio_unidad")
                    );
                    detalles.add(detalle);
                }

                Venta venta = new Venta(
                        rs.getInt("id"),
                        trabajadorDAO.read(rs.getInt("id_vendedor")),
                        detalles,
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("dni_comprador")
                );
                return venta;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Integer id) throws SQLException {

    }

    @Override
    public void delete(Integer id) throws SQLException {

    }

    @Override
    public List<Venta> readAll() {
        String query = "SELECT * FROM venta";
        List<Venta> ventas = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            TrabajadorDAOImpl trabajadorDAO = new TrabajadorDAOImpl();
            while (rs.next()) {
                PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM detalleventa WHERE id_venta = ?");
                stmt2.setInt(1, rs.getInt("id"));
                ResultSet rs2 = stmt2.executeQuery();
                List<DetalleVenta> detalles = new ArrayList<>();
                FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();
                while (rs2.next()) {
                    System.out.println("ID DE FARMACO: " + rs2.getInt("id_farmaco"));
                    DetalleVenta detalle = new DetalleVenta(
                            farmacoDAO.read(rs2.getInt("id_farmaco")),
                            rs2.getByte("cantidad"),
                            rs2.getFloat("precio_unidad")
                    );
                    detalles.add(detalle);
                }

                Venta venta = new Venta(
                        rs.getInt("id"),
                        trabajadorDAO.read(rs.getInt("id_vendedor")),
                        detalles,
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getString("dni_comprador")
                );
                ventas.add(venta);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ventas;
    }
}

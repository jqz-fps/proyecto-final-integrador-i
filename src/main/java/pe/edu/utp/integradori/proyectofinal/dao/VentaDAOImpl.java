package pe.edu.utp.integradori.proyectofinal.dao;

import pe.edu.utp.integradori.proyectofinal.interfaces.VentaDAO;
import pe.edu.utp.integradori.proyectofinal.model.Categoria;
import pe.edu.utp.integradori.proyectofinal.model.DetalleVenta;
import pe.edu.utp.integradori.proyectofinal.model.Farmaco;
import pe.edu.utp.integradori.proyectofinal.model.Venta;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl extends Conexion implements VentaDAO {
    @Override
    public void create(Venta venta) throws SQLException {
        for (DetalleVenta detalle : venta.getDetalles()) {
            FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();
            Farmaco farmaco = farmacoDAO.read(detalle.getFarmaco().getId());
            if (farmaco.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("El stock de " + farmaco.getNombre() + " es insuficiente");
            }
        }
        Connection conn = null;
        try {
            conn = getConnection();

            conn.setAutoCommit(false);
            String callInsertVenta = "{CALL InsertarVenta(?, ?, ?)}";
            int idVenta = 0;
            try (CallableStatement stmt = conn.prepareCall(callInsertVenta)) {
                stmt.setInt(1, venta.getVendedor().getId());
                stmt.setString(2, venta.getDni_comprador());
                LocalDateTime fecha = venta.getFecha();
                String formattedFecha = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                stmt.setString(3, formattedFecha);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idVenta = rs.getInt("id_venta");
                    }
                }
            }

            String queryDetalles = "INSERT INTO detalleventa (id_venta, id_farmaco, cantidad, precio_unidad) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmtDetalles = conn.prepareStatement(queryDetalles)) {
                for (DetalleVenta detalle : venta.getDetalles()) {
                    FarmacoDAOImpl farmacoDAO = new FarmacoDAOImpl();
                    detalle.getFarmaco().setStock(detalle.getFarmaco().getStock() - detalle.getCantidad());
                    farmacoDAO.update(detalle.getFarmaco());

                    stmtDetalles.setInt(1, idVenta);
                    stmtDetalles.setInt(2, detalle.getFarmaco().getId());
                    stmtDetalles.setByte(3, detalle.getCantidad());
                    stmtDetalles.setFloat(4, detalle.getPrecio_unidad());
                    stmtDetalles.addBatch();
                }

                stmtDetalles.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Error al hacer rollback", ex);
                }
            }
            throw new RuntimeException("Error al crear la venta", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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
    public void update(Venta venta) throws SQLException {

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

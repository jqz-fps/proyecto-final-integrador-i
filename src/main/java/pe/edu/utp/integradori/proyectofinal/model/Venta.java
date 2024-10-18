package pe.edu.utp.integradori.proyectofinal.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Venta {
    private int id;
    private Trabajador vendedor;
    private ArrayList<DetalleVenta> detalles;
    private LocalDateTime fecha;
    private String dni_comprador;

    public Venta(int id, Trabajador vendedor, ArrayList<DetalleVenta> detalles, LocalDateTime fecha, String dni_comprador) {
        this.id = id;
        this.vendedor = vendedor;
        this.detalles = detalles;
        this.fecha = fecha;
        this.dni_comprador = dni_comprador;
    }

    public int getId() {
        return id;
    }

    public Trabajador getVendedor() {
        return vendedor;
    }

    public ArrayList<DetalleVenta> getDetalles() {
        return detalles;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getDni_comprador() {
        return dni_comprador;
    }

    public float getTotal() {
        float total = 0;
        for (DetalleVenta detalle : detalles) {
            total += (detalle.getCantidad() * detalle.getPrecio_unidad());
        }
        return total;
    }

    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}

package pe.edu.utp.integradori.proyectofinal.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;

public class Venta {
    private int id;
    private Trabajador vendedor;
    private List<DetalleVenta> detalles;
    private LocalDateTime fecha;
    private String dni_comprador;

    public Venta(int id, Trabajador vendedor, List<DetalleVenta> detalles, LocalDateTime fecha, String dni_comprador) {
        checkArgument(id > 0, "El id debe ser mayor a cero");
        checkNotNull(vendedor, "El vendedor no puede ser nulo");
        checkNotNull(detalles, "El arreglo de detalles no puede ser nulo");
        checkNotNull(fecha, "La fecha no puede ser nula");
        checkNotNull(dni_comprador, "El dni del comprador no puede ser nulo");
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

    public List<DetalleVenta> getDetalles() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setVendedor(Trabajador vendedor) {
        this.vendedor = vendedor;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setDni_comprador(String dni_comprador) {
        this.dni_comprador = dni_comprador;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", vendedor=" + vendedor +
                ", detalles=" + detalles +
                ", fecha=" + fecha +
                ", dni_comprador='" + dni_comprador + '\'' +
                '}';
    }
}

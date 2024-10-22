package pe.edu.utp.integradori.proyectofinal.model;

import static com.google.common.base.Preconditions.*;

public class DetalleVenta {
    private Farmaco farmaco;
    private byte cantidad;
    private float precio_unidad;

    public DetalleVenta(Farmaco farmaco, byte cantidad, float precio_unidad) {
        checkArgument(cantidad > 0, "La cantidad debe ser mayor a cero");
        checkArgument(precio_unidad > 0, "El precio debe ser mayor a cero");
        this.farmaco = farmaco;
        this.cantidad = cantidad;
        this.precio_unidad = precio_unidad;
    }

    public Farmaco getFarmaco() {
        return farmaco;
    }

    public byte getCantidad() {
        return cantidad;
    }

    public float getPrecio_unidad() {
        return precio_unidad;
    }

    public float calcularTotal() {
        return cantidad * precio_unidad;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "farmaco=" + farmaco +
                ", cantidad=" + cantidad +
                ", precio_unidad=" + precio_unidad +
                '}';
    }
}

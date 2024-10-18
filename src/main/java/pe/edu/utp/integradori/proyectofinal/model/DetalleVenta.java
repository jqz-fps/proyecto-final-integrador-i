package pe.edu.utp.integradori.proyectofinal.model;

public class DetalleVenta {
    private Farmaco farmaco;
    private byte cantidad;
    private float precio_unidad;

    public DetalleVenta(Farmaco farmaco, byte cantidad, float precio_unidad) {
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
}

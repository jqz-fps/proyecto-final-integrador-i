package pe.edu.utp.integradori.proyectofinal.model;

public class Farmaco {
    private int id;
    private Laboratorio laboratorio;
    private Categoria categoria;
    private Distribuidora distribuidora;
    private int stock;
    private String nombre;
    private String descripcion;
    private String presentacion;
    private String composicion;
    private float precio;

    public Farmaco(int id, Laboratorio laboratorio, Categoria categoria, Distribuidora distribuidora, int stock, String nombre, String descripcion, String presentacion, String composicion, float precio) {
        this.id = id;
        this.laboratorio = laboratorio;
        this.categoria = categoria;
        this.distribuidora = distribuidora;
        this.stock = stock;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
        this.composicion = composicion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Distribuidora getDistribuidora() {
        return distribuidora;
    }

    public int getStock() {
        return stock;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public String getComposicion() {
        return composicion;
    }

    public float getPrecio() {
        return precio;
    }
}

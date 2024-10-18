package pe.edu.utp.integradori.proyectofinal.model;

import static com.google.common.base.Preconditions.*;

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

    public Farmaco(int id, Laboratorio laboratorio, Categoria categoria,
                   Distribuidora distribuidora, int stock, String nombre, String descripcion,
                   String presentacion, String composicion, float precio) {
        checkArgument(id > 0, "El id debe ser mayor a cero");
        checkNotNull(laboratorio, "El laboratorio no puede ser nulo");
        checkNotNull(categoria, "La categoria no puede ser nula");
        checkNotNull(distribuidora, "La distribuidora no puede ser nula");
        checkArgument(stock > 0, "El stock debe ser mayor a cero");
        checkNotNull(nombre, "El nombre no puede ser nulo");
        checkNotNull(descripcion, "La descripcion no puede ser nula");
        checkNotNull(presentacion, "La presentacion no puede ser nula");
        checkNotNull(composicion, "La composicion no puede ser nula");
        checkArgument(precio > 0, "El precio debe ser mayor a cero");
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

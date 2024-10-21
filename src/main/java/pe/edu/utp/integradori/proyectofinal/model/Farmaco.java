package pe.edu.utp.integradori.proyectofinal.model;

import java.util.List;

import static com.google.common.base.Preconditions.*;

public class Farmaco {
    private int id;
    private Laboratorio laboratorio;
    private List<Categoria> categorias;
    private Distribuidora distribuidora;
    private int stock;
    private String nombre;
    private String descripcion;
    private String presentacion;
    private String composicion;
    private float precio;

    public Farmaco(int id, Laboratorio laboratorio, List<Categoria> categorias,
                   Distribuidora distribuidora, int stock, String nombre, String descripcion,
                   String presentacion, String composicion, float precio) {
        checkArgument(id > 0, "El id debe ser mayor a cero");
        checkArgument(stock > 0, "El stock debe ser mayor a cero");
        checkNotNull(nombre, "El nombre no puede ser nulo");
        checkNotNull(descripcion, "La descripcion no puede ser nula");
        checkNotNull(presentacion, "La presentacion no puede ser nula");
        checkNotNull(composicion, "La composicion no puede ser nula");
        checkArgument(precio > 0, "El precio debe ser mayor a cero");
        this.id = id;
        this.laboratorio = laboratorio;
        this.categorias = categorias;
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

    public List<Categoria> getCategoria() {
        return categorias;
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

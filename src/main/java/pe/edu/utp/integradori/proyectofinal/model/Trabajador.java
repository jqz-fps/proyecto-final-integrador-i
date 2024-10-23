package pe.edu.utp.integradori.proyectofinal.model;

import com.google.common.base.Joiner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.*;

public class Trabajador {
    private int id;
    private String dni;
    private String nombres;
    private String ap_paterno;
    private String ap_materno;
    private char genero;
    private LocalDate fecha_nacimiento;
    private LocalDateTime fecha_registro;
    private String correo;
    private String telefono;
    private List<Rol> roles;
    private List<Venta> ventas;

    public Trabajador(int id, String dni, String nombres, String ap_paterno,
                      String ap_materno, char genero, LocalDate fecha_nacimiento,
                      LocalDateTime fecha_registro, List<Rol> roles, List<Venta> ventas) {
        checkNotNull(dni, "El dni no puede ser nulo");
        checkNotNull(nombres, "El nombre no puede ser nulo");
        checkNotNull(ap_paterno, "El apellido paterno no puede ser nulo");
        checkNotNull(ap_materno, "El apellido materno no puede ser nulo");
        checkNotNull(roles, "El arreglo de roles no puede ser nulo");
        this.id = id;
        this.dni = dni;
        this.nombres = nombres;
        this.ap_paterno = ap_paterno;
        this.ap_materno = ap_materno;
        this.genero = genero;
        this.fecha_nacimiento = fecha_nacimiento;
        this.fecha_registro = fecha_registro;
        this.roles = roles;
        this.ventas = ventas;
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombres() {
        return nombres;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public char getGenero() {
        return genero;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getFecha_nacimientoFormatoInput() {
        return fecha_nacimiento != null ? fecha_nacimiento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
    }

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public boolean hasRole(Rol rol) {
        return roles.contains(rol);
    }

    public List<Venta> getVentas() {
        return ventas;
    }

    public String getApellidos() {
        return ap_paterno + " " + ap_materno;
    }

    public String getFechaRegistroFormateada() {
        return fecha_registro != null ? fecha_registro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "No disponible";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRolesString() {
        String roles = "";
        Joiner joiner = Joiner.on("\n").skipNulls();
        return joiner.join(this.roles);
    }

    public String getGeneroParseado() {
        return genero == 'M' ? "Masculino" : "Feminino";
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public void setFecha_registro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
}

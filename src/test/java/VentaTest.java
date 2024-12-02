import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.utp.integradori.proyectofinal.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VentaTest {

    static Trabajador vendedor = new Trabajador(
                1, "12345678", "Cristhian", "Zeña", "Bances",
                        'M', LocalDate.of(2004, 1, 12), LocalDateTime.now(),
                new ArrayList<Rol>(), new ArrayList<Venta>()
                        );
    static Farmaco farmaco1;
    static Farmaco farmaco2;
    static Farmaco farmaco3;
    static Farmaco farmaco4;

    @BeforeEach
    public void setUp() {
        farmaco1 = new Farmaco(
                1,
                new Laboratorio(1, "Laboratorio A"),
                List.of(new Categoria(1, "Antibiótico")),
                new Distribuidora(1, "Distribuidora", "Dist", "dist@tribui.dora", "12345678"),
                50,
                "Amoxicilina",
                "Antibiótico de amplio espectro",
                "Tabletas",
                "Amoxicilina 500mg",
                12.50f
        );
        farmaco2 = new Farmaco(
                2,
                new Laboratorio(2, "Laboratorio B"),
                List.of(new Categoria(2, "Analgésico")),
                new Distribuidora(2, "Distribuidora B", "DistB", "contacto@distribuidorab.com", "98765432"),
                100,
                "Paracetamol",
                "Analgésico y antipirético",
                "Cápsulas",
                "Paracetamol 500mg",
                8.75f
        );
        farmaco3 = new Farmaco(
                3,
                new Laboratorio(3, "Laboratorio C"),
                List.of(new Categoria(3, "Antihistamínico")),
                new Distribuidora(3, "Distribuidora C", "DistC", "info@distc.com", "45678912"),
                200,
                "Loratadina",
                "Antihistamínico de segunda generación",
                "Tabletas",
                "Loratadina 10mg",
                15.00f
        );
        farmaco4 = new Farmaco(
                4,
                new Laboratorio(4, "Laboratorio D"),
                List.of(new Categoria(4, "Antiséptico")),
                new Distribuidora(4, "Distribuidora D", "DistD", "soporte@distd.com", "32165487"),
                300,
                "Povidona Yodada",
                "Antiséptico para heridas",
                "Solución",
                "Povidona Yodada 10%",
                22.30f
        );
    }

    @Test
    public void testA() {
        List<DetalleVenta> detalles = null; // Venta sin detalles
        LocalDateTime fecha = LocalDateTime.now();
        String dni_comprador = "12345678";
        Exception exception = assertThrows(Exception.class, () -> {
            new Venta(1, vendedor, detalles, fecha, dni_comprador);
        });
        assertEquals("El arreglo de detalles no puede ser nulo", exception.getMessage());
    }

    @Test
    public void testB() {
        List<DetalleVenta> detalles = Arrays.asList(
                new DetalleVenta(farmaco1, (byte) 2, 12.50f),
                new DetalleVenta(farmaco2, (byte) 1, 8.75f)
        );
        Venta venta = new Venta(2, vendedor, detalles, LocalDateTime.now(), "87654321");

        float totalEsperado = (2 * 12.50f) + (1 * 8.75f);
        assertEquals(totalEsperado, venta.getTotal(), 0.01);
    }

    @Test
    public void testC() {
        List<DetalleVenta> detalles = Arrays.asList(
                new DetalleVenta(farmaco3, (byte) 3, 15.00f)
        );
        LocalDateTime fecha = LocalDateTime.of(2024, 11, 6, 15, 30);
        Venta venta = new Venta(4, vendedor, detalles, fecha, "98765432");

        assertEquals("06/11/2024 15:30", venta.getFechaFormateada());
    }

}

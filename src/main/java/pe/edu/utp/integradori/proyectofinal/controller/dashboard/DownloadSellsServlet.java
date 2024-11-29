package pe.edu.utp.integradori.proyectofinal.controller.dashboard;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.edu.utp.integradori.proyectofinal.dao.VentaDAOImpl;
import pe.edu.utp.integradori.proyectofinal.model.Rol;
import pe.edu.utp.integradori.proyectofinal.model.Trabajador;
import pe.edu.utp.integradori.proyectofinal.model.Venta;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "downloadSellsServlet", value = "/dashboard/sells/download")
public class DownloadSellsServlet extends jakarta.servlet.http.HttpServlet {

    public static Logger logger = LoggerFactory.getLogger(DownloadSellsServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Trabajador trabajador = (Trabajador) request.getSession().getAttribute("usuario");
        if(!trabajador.hasRole(Rol.Supervisor)) {
            response.sendRedirect(request.getContextPath() + "/dashboard/sells");
            return;
        }

        VentaDAOImpl ventaDAO = new VentaDAOImpl();
        List<Venta> ventas = ventaDAO.readAll();
        ventas.sort(Comparator.comparing(Venta::getId).reversed());

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ventas");

        Row headerRow = sheet.createRow(0);
        createCell(workbook, headerRow, 0, "ID Venta");
        createCell(workbook, headerRow, 1, "Vendedor Nombres");
        createCell(workbook, headerRow, 2, "Vendedor Apellidos");
        createCell(workbook, headerRow, 3, "DNI Comprador");
        createCell(workbook, headerRow, 4, "Fecha de Venta");
        createCell(workbook, headerRow, 5, "Total");

        int rowCount = 1;
        for (Venta venta : ventas) {
            Row row = sheet.createRow(rowCount++);
            createCell(workbook, row, 0, venta.getId());
            createCell(workbook, row, 1, venta.getVendedor().getNombres());
            createCell(workbook, row, 2, venta.getVendedor().getApellidos());
            createCell(workbook, row, 3, venta.getDni_comprador());
            createCell(workbook, row, 4, venta.getFecha());
            createCell(workbook, row, 5, venta.getTotal());
        }

        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ventas.xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        logger.info("El trabajador " + ((Trabajador) request.getSession().getAttribute("usuario")).getId() +
                " ha descargado el formato excel de las ventas");

        response.sendRedirect(request.getContextPath() + "/dashboard/sells");
    }

    private static void createCell(Workbook wb, Row row, int column, Object val,
                                   HorizontalAlignment halign,
                                   VerticalAlignment valign) {
        CreationHelper createHelper = wb.getCreationHelper();
        Cell cell = row.createCell(column);
        CellStyle cellStyle = wb.createCellStyle();

        if (val instanceof String) {
            cell.setCellValue((String) val);
        } else if (val instanceof Integer) {
            cell.setCellValue((Integer) val);
        } else if (val instanceof Double) {
            cell.setCellValue((Double) val);
        } else if (val instanceof Float) {
            cell.setCellValue((Float) val);
        } else if (val instanceof LocalDate) {
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
            cell.setCellValue((LocalDate) val);
        } else if (val instanceof LocalDateTime) {
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
            cell.setCellValue(((LocalDateTime) val).toString());
        } else if (val instanceof Boolean) {
            cell.setCellValue((Boolean) val);
        } else {
            cell.setCellValue(val.toString());
        }

        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cell.setCellStyle(cellStyle);
    }

    private static void createCell(Workbook wb, Row row, int column, Object val) {
        createCell(wb, row, column, val, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
    }
}

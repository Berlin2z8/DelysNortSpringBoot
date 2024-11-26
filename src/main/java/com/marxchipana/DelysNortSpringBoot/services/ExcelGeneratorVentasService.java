package com.marxchipana.DelysNortSpringBoot.services;


import com.marxchipana.DelysNortSpringBoot.models.Venta;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelGeneratorVentasService {
    public ByteArrayInputStream generateVentaReportExcel(List<Venta> ventas) {
        String[] COLUMNs = {"ID", "Cliente", "NombreProducto", "Cantidad", "Total", "Email", "Fecha"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Ventas");

            // Crear encabezados de la tabla
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < COLUMNs.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(COLUMNs[i]);
            }

            // Llenar la tabla con datos de los productos
            int rowIndex = 1;
            for (Venta venta : ventas) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(venta.getId());
                row.createCell(1).setCellValue(venta.getNombre());
                row.createCell(2).setCellValue(venta.getNombresProductos());
                row.createCell(3).setCellValue(venta.getCantidad());
                row.createCell(4).setCellValue(venta.getTotal().doubleValue());
                row.createCell(5).setCellValue(venta.getEmail());
                row.createCell(6).setCellValue(venta.getFecha().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

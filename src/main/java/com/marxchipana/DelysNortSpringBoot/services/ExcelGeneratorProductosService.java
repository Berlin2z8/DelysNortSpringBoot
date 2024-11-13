package com.marxchipana.DelysNortSpringBoot.services;

import com.marxchipana.DelysNortSpringBoot.models.Producto;
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
public class ExcelGeneratorProductosService {
    public ByteArrayInputStream generateProductReportExcel(List<Producto> productos) {
        String[] COLUMNs = {"ID", "Nombre", "Descripción", "Precio", "Imagen"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Productos");

            // Crear encabezados de la tabla
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < COLUMNs.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(COLUMNs[i]);
            }

            // Llenar la tabla con datos de los productos
            int rowIndex = 1;
            for (Producto producto : productos) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(producto.getId());
                row.createCell(1).setCellValue(producto.getNombre());
                row.createCell(2).setCellValue(producto.getDescripcion());
                row.createCell(3).setCellValue(producto.getPrecio().doubleValue());
                row.createCell(4).setCellValue(producto.getImagen());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

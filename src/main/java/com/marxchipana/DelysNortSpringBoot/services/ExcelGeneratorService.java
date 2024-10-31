package com.marxchipana.DelysNortSpringBoot.services;

import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelGeneratorService {

    public ByteArrayInputStream generateUserExcelReport(List<Usuario> usuarios) {
        String[] columns = {"ID", "Nombre", "Email", "Rol", "Fecha de Registro"};

        // Crear un libro de trabajo y una hoja
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Usuarios");

            // Crear la fila de encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(createHeaderCellStyle(workbook));
            }

            // Llenar la hoja con datos de los usuarios
            for (int i = 0; i < usuarios.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Usuario usuario = usuarios.get(i);
                row.createCell(0).setCellValue(usuario.getId());
                row.createCell(1).setCellValue(usuario.getNombre());
                row.createCell(2).setCellValue(usuario.getEmail());
                row.createCell(3).setCellValue(usuario.getRol().getNombre());
                row.createCell(4).setCellValue(usuario.getFechaRegistro().toString());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }
}

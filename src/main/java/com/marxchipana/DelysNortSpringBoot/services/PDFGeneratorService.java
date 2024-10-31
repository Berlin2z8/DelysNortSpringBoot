package com.marxchipana.DelysNortSpringBoot.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PDFGeneratorService {

    public ByteArrayInputStream generateUserReport(List<Usuario> usuarios) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Crear PdfWriter y PdfDocument a partir del OutputStream
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Agregar título y subtítulo
            document.add(new Paragraph("Reporte de Usuarios").setBold().setFontSize(18));
            document.add(new Paragraph("Lista de Usuarios Registrados").setFontSize(12));

            // Crear la tabla con columnas
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 3, 2, 3}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados de la tabla
            table.addHeaderCell("ID");
            table.addHeaderCell("Nombre");
            table.addHeaderCell("Email");
            table.addHeaderCell("Rol");
            table.addHeaderCell("Fecha de Registro");

            // Llenar la tabla con datos de los usuarios
            for (Usuario usuario : usuarios) {
                table.addCell(String.valueOf(usuario.getId()));
                table.addCell(usuario.getNombre());
                table.addCell(usuario.getEmail());
                table.addCell(usuario.getRol().getNombre());
                table.addCell(usuario.getFechaRegistro().toString());
            }

            // Agregar la tabla al documento y cerrarlo
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}

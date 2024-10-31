package com.marxchipana.DelysNortSpringBoot.services;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.marxchipana.DelysNortSpringBoot.models.Producto;
import org.springframework.stereotype.Service;

@Service
public class PDFGeneratorProductosService {

    public ByteArrayInputStream generateProductReport(List<Producto> productos) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Crea el PdfWriter utilizando el OutputStream
        PdfWriter pdfWriter = new PdfWriter(out);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        try (Document document = new Document(pdfDocument)) {
            document.add(new Paragraph("Reporte de Productos")
                    .setBold().setFontSize(18).setMarginBottom(10));
            document.add(new Paragraph("Lista de Productos Registrados")
                    .setFontSize(12).setMarginBottom(20));

            // Crear tabla con columnas
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 4, 2, 3}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados de la tabla
            table.addHeaderCell("ID");
            table.addHeaderCell("Nombre");
            table.addHeaderCell("Descripción");
            table.addHeaderCell("Precio");
            table.addHeaderCell("Imagen");

            // Llenar la tabla con los productos
            for (Producto producto : productos) {
                table.addCell(String.valueOf(producto.getId()));
                table.addCell(producto.getNombre());
                table.addCell(producto.getDescripcion());
                table.addCell(producto.getPrecio().toString());

                // Agregar imagen del producto
                try {
                    Image img = new Image(ImageDataFactory.create(producto.getImagen()))
                            .scaleToFit(50, 50); // Escalar la imagen
                    table.addCell(img);
                } catch (Exception e) {
                    table.addCell("Imagen no disponible");
                }
            }

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar PdfDocument para liberar recursos
            pdfDocument.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}

package com.marxchipana.DelysNortSpringBoot.services;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import com.marxchipana.DelysNortSpringBoot.models.Venta;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class PDFGeneratorVentasService {
    public ByteArrayInputStream generateSalesReport(List<Venta> ventas) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Agregar logo
            String logoPath = "src/main/resources/static/images/delys/logodelysnort.jpg";
            ImageData logoData = ImageDataFactory.create(logoPath);
            Image logo = new Image(logoData).scaleToFit(50, 50);
            document.add(logo);

            // Título y fecha de reporte
            document.add(new Paragraph("Reporte de Ventas").setBold().setFontSize(18));
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            document.add(new Paragraph("Fecha de reporte: " + currentDate).setFontSize(10).setItalic());

            // Agregar la información de la empresa
            document.add(new Paragraph("\nEl entorno en el que opera Delis North Snacks es clave para el crecimiento y éxito de la empresa. Nos encontramos en un mercado en auge, donde los consumidores están cada vez más interesados en productos saludables y naturales. Esta tendencia global ha creado una gran oportunidad para snacks como los nuestros, hechos a base de ingredientes locales, como el plátano y el camote.\n")
                    .setFontSize(10)
                    .setMarginTop(10));
            document.add(new Paragraph("En Perú, contamos con una rica diversidad agrícola que nos permite acceder a ingredientes de alta calidad, frescos y nutritivos. Además, estamos comprometidos con prácticas sostenibles, apoyando a agricultores locales y pequeñas comunidades, lo que nos permite mantener una cadena de suministro ética y responsable.\n")
                    .setFontSize(10));
            document.add(new Paragraph("A nivel externo, el mercado de snacks saludables está en constante crecimiento tanto a nivel local como internacional, lo que abre puertas para que nuestros productos lleguen a nuevas regiones. Sin embargo, este entorno también es competitivo, con muchas empresas enfocándose en la alimentación saludable. Por eso, nuestra estrategia se basa en ofrecer productos auténticos y de calidad que destaquen en sabor y nutrición.\n")
                    .setFontSize(10));

            // Información de contexto
            document.add(new Paragraph("\nEste reporte presenta un resumen de las ventas realizadas.").setFontSize(10));

            // Total de ventas
            document.add(new Paragraph("\nTotal de ventas: " + ventas.size()).setBold().setFontSize(12));

            // Tabla de ventas
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 3, 2, 2, 2, 2, 3}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezados de la tabla
            table.addHeaderCell("ID");
            table.addHeaderCell("Nombre del Cliente");
            table.addHeaderCell("Email");
            table.addHeaderCell("Celular");
            table.addHeaderCell("Productos");
            table.addHeaderCell("Cantidad");
            table.addHeaderCell("Total");
            table.addHeaderCell("Fecha de Venta");

            // Llenado de la tabla
            for (Venta venta : ventas) {
                table.addCell(String.valueOf(venta.getId()));
                table.addCell(venta.getNombre());
                table.addCell(venta.getEmail());
                table.addCell(venta.getCelular());
                table.addCell(venta.getNombresProductos());
                table.addCell(String.valueOf(venta.getCantidad()));
                table.addCell(String.valueOf(venta.getTotal()));
                table.addCell(venta.getFecha().toString());
            }

            document.add(table);

            // Mensaje de confidencialidad
            document.add(new Paragraph("\n\nEste documento contiene información confidencial de Delis North Snacks. Uso interno únicamente.")
                    .setFontSize(10).setItalic());
            document.add(new Paragraph("La divulgación no autorizada puede estar sujeta a sanciones legales.")
                    .setFontSize(10).setItalic());

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}

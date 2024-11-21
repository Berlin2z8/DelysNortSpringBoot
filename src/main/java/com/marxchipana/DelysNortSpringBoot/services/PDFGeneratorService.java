package com.marxchipana.DelysNortSpringBoot.services;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

            // Cargar y agregar el logo
            String logoPath = "src/main/resources/static/images/delys/logodelysnort.jpg";
            ImageData logoData = ImageDataFactory.create(logoPath);
            Image logo = new Image(logoData).scaleToFit(50, 50);  // Ajusta el tamaño según lo necesites
            document.add(logo);

            // Agregar título y subtítulo
            document.add(new Paragraph("Reporte de Usuarios").setBold().setFontSize(18));
            document.add(new Paragraph("Lista de Usuarios Registrados").setFontSize(12));

            // Agregar la fecha de generación del reporte
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

            // Agregar la cantidad total de usuarios registrados
            document.add(new Paragraph("\nTotal de usuarios registrados: " + usuarios.size()).setBold().setFontSize(12));

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

            // Agregar la tabla al documento
            document.add(table);

            // Agregar mensaje de confidencialidad
            document.add(new Paragraph("\n\nEste documento contiene información confidencial de Delis North Snacks. La información aquí presentada es exclusivamente para uso interno de la empresa y no debe ser compartida o distribuida sin autorización.")
                    .setFontSize(10)
                    .setItalic());
            document.add(new Paragraph("La divulgación no autorizada de este documento puede estar sujeta a sanciones legales.")
                    .setFontSize(10)
                    .setItalic());

            // Cerrar el documento
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
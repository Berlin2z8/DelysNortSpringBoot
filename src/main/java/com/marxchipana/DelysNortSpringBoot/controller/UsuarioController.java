package com.marxchipana.DelysNortSpringBoot.controller;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.marxchipana.DelysNortSpringBoot.DAO.CarritoRepository;
import com.marxchipana.DelysNortSpringBoot.DAO.ContactoRepository;
import com.marxchipana.DelysNortSpringBoot.DAO.VentaRepository;
import com.marxchipana.DelysNortSpringBoot.models.*;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryProducto;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryRol;
import com.marxchipana.DelysNortSpringBoot.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class UsuarioController {

    //Aca se llama a los repositorios de cada clase para poder hacer las interaccioens
    @Autowired
    private RepositoryProducto productoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private UsuarioService usuarioService; // Inyección del servicio

    @Autowired
    private ContactoRepository contactoRepository;

    @PostMapping("/guardarContacto")
    public String guardarContacto(@ModelAttribute Contacto contacto, Model model) {
        // Guardar los datos en la base de datos
        contactoRepository.save(contacto);

        // Agregar un mensaje de éxito al modelo
        model.addAttribute("mensaje", "¡Tu solicitud fue enviada con éxito!");

        // Redirigir a la página de contacto con el mensaje
        return "redirect:/cliente";
    }

    // Método con @ModelAttribute para hacer disponible la cantidad del carrito en todas las páginas
    @ModelAttribute("cantidadCarrito")
    public int cantidadCarrito() {
        return carritoRepository.findAll().size(); // Cantidad de productos en el carrito
    }

    @GetMapping("/cliente")
    public String usuarioPage(Model model, Principal principal) {
        // Verifica si el principal es null
        if (principal == null) {
            return "redirect:/login"; // Redirige al login si no está autenticado
        }
        var productos = productoRepository.findAll();
        var carrito = carritoRepository.findAll();

        var totalCarrito = carrito.stream()
                .map(item -> item.getProducto().getPrecio()
                        .multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("productos", productos);
        model.addAttribute("carrito", carrito);
        model.addAttribute("totalCarrito", totalCarrito);

        // Agregar un objeto vacío de contacto para el formulario
        model.addAttribute("contacto", new Contacto());
        return "dashboard";
    }

    @GetMapping("/cliente/nosotros")
    public String nosotros(Model model) {
        var carrito = carritoRepository.findAll();

        model.addAttribute("carrito", carrito);
        return "nosotros";
    }



    @Autowired
    private RepositoryRol rolRepository; // Repositorio de roles
    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        // Asignar el rol por defecto ROL_CLIENTE
        Rol rolCliente = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setRol(rolCliente);
        usuario.setFechaRegistro(new Date());

        usuarioService.guardarUsuario(usuario); // Guardar el usuario usando el servicio
        return "redirect:/login"; // Redirigir al login tras el registro
    }

    @PostMapping("/carrito/agregar/{id}")
    public String agregarAlCarrito(@PathVariable("id") Integer id) {
        var producto = productoRepository.findById(id).orElseThrow();
        var carrito = new Carrito();
        carrito.setProducto(producto);
        carrito.setCantidad(1);
        carritoRepository.save(carrito);
        return "redirect:/cliente";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(value = "productoId", required = false) Integer productoId,
                                    Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioService.findByEmail(principal.getName());
        List<Producto> productos;

        if (productoId != null) {
            Producto producto = productoRepository.findById(productoId).orElseThrow();
            productos = List.of(producto); // Lista con un solo producto
        } else {
            productos = carritoRepository.findAll().stream()
                    .map(Carrito::getProducto)
                    .toList();
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("productos", productos);
        model.addAttribute("venta", new Venta()); // Asegúrate de que 'venta' esté correctamente inicializada

        return "formularioVenta";
    }

    @PostMapping("/guardar")
    public String guardarVenta(
            @ModelAttribute Venta venta,
            @RequestParam("tipoDocumento") String tipoDocumento,
            Principal principal,
            HttpServletResponse response) throws IOException {

        if (principal == null) {
            return "redirect:/login";
        }

        // Obtener el usuario actual
        Usuario usuario = usuarioService.findByEmail(principal.getName());
        venta.setUsuario(usuario);
        venta.setNombre(usuario.getNombre());
        venta.setEmail(usuario.getEmail());
        venta.setCelular(venta.getCelular());

        // Guardar la venta en la base de datos
        ventaRepository.save(venta);
        carritoRepository.deleteAll(); // Vaciar el carrito

        // Generar el documento según el tipo seleccionado
        if ("boleta".equals(tipoDocumento)) {
            response.sendRedirect("/generarBoleta?id=" + venta.getId());
        } else if ("factura".equals(tipoDocumento)) {
            response.sendRedirect("/generarFactura?id=" + venta.getId());
        }

        return null; // Evitar redirección adicional
    }


    private List<Carrito> carrito;

    @DeleteMapping("/carrito/eliminar/{id}")
    public String eliminarProductoDelCarrito(@PathVariable("id") Integer id) {
        carritoRepository.deleteById(id);  // Eliminar de la base de datos, del carrito que hay en el nav
        return "redirect:/cliente";  // Redirigir a la página del cliente para actualizar la vista, lo puedes cambiar si quieres otro apartado solo para productos
    }

    //ESto es para generar la boleta de cada compra
    @GetMapping("/generarBoleta")
    public void generarBoleta(@RequestParam("id") Integer ventaId, HttpServletResponse response) throws IOException {
        // Buscar la última venta registrada
        Venta ultimaVenta = ventaRepository.findTopByOrderByIdDesc();
        if (ultimaVenta == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró ninguna venta.");
            return;
        }

        // Configurar la respuesta HTTP para enviar el PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=boleta.pdf");

        // Crear el PDF
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Agregar logo y nombre de la empresa
        String logoPath = "classpath:static/images/delys/logodelysnort.jpg"; // Ruta dentro de 'resources'

        Image logo = new Image(ImageDataFactory.create(logoPath)).scaleToFit(100, 100);
        Table headerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        headerTable.addCell(logo);
        headerTable.addCell(new Paragraph("DelysNortSnack")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));
        document.add(headerTable);

        // Encabezado de la boleta
        document.add(new Paragraph("Boleta de Venta")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        // Información de la venta
        document.add(new Paragraph("Fecha: " + ultimaVenta.getFecha())
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Cliente: " + ultimaVenta.getNombre())
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Email: " + ultimaVenta.getEmail())
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Celular: " + ultimaVenta.getCelular())
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        // Productos comprados
        document.add(new Paragraph("Productos Comprados:").setBold().setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(ultimaVenta.getNombresProductos())
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        // Total
        document.add(new Paragraph("Total: S/ " + ultimaVenta.getTotal())
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        // Generar código QR como token único
        // Generar código QR como token único
        String token = "Venta-" + ultimaVenta.getId() + "-" + ultimaVenta.getFecha().getTime();
        BarcodeQRCode qrCode = new BarcodeQRCode(token);
        Image qrCodeImage = new Image(qrCode.createFormXObject(pdf))
                .scaleToFit(100, 100)
                .setHorizontalAlignment(HorizontalAlignment.CENTER); // Usar directamente la constante

        // Agregar al documento
        document.add(new Paragraph("Código QR:").setTextAlignment(TextAlignment.CENTER));
        document.add(qrCodeImage);

        // Nombre de la empresa al pie
        document.add(new Paragraph("Gracias por su compra en DelysNortSnack")
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20));
        document.add(new Paragraph("Visítanos pronto").setTextAlignment(TextAlignment.CENTER));

        // Cerrar el documento
        document.close();
    }

    @GetMapping("/generarFactura")
    public void generarFactura(
            @RequestParam("id") Integer ventaId,
            @RequestParam("ruc") String ruc,
            @RequestParam("direccion") String direccion,
            @RequestParam("razonSocial") String razonSocial,
            HttpServletResponse response) throws IOException {

        // Buscar la venta por ID
        Venta ultimaVenta = ventaRepository.findTopByOrderByIdDesc();
        if (ultimaVenta == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró la venta.");
            return;
        }

        // Configurar la respuesta HTTP para enviar el PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=factura.pdf");

        // Crear el PDF
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Agregar logo y nombre de la empresa
        String logoPath = "classpath:static/images/delys/logodelysnort.jpg";
        Image logo = new Image(ImageDataFactory.create(logoPath)).scaleToFit(100, 100);
        Table headerTable = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
        headerTable.addCell(logo);
        headerTable.addCell(new Paragraph("DelysNortSnack")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));
        document.add(headerTable);

        // Encabezado de la factura
        document.add(new Paragraph("Factura de Venta")
                .setBold()
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

        // Información del cliente
        document.add(new Paragraph("RUC: " + ruc).setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("Razón Social: " + razonSocial).setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("Dirección: " + direccion).setTextAlignment(TextAlignment.LEFT).setMarginBottom(10));

        // Información de la venta
        document.add(new Paragraph("Número de Factura: " + ultimaVenta.getId()).setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("Fecha: " + ultimaVenta.getFecha()).setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("Cliente: " + ultimaVenta.getNombre()).setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("Email: " + ultimaVenta.getEmail()).setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("Celular: " + ultimaVenta.getCelular()).setTextAlignment(TextAlignment.LEFT).setMarginBottom(10));

        // Detalle de productos
        document.add(new Paragraph("Detalle de Productos:").setBold().setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(ultimaVenta.getNombresProductos()).setTextAlignment(TextAlignment.LEFT).setMarginBottom(10));

        // Total
        document.add(new Paragraph("Total: S/ " + ultimaVenta.getTotal())
                .setBold()
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(10));

        // Pie de página
        document.add(new Paragraph("Gracias por su compra en DelysNortSnack")
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(20));
        document.add(new Paragraph("Visítanos pronto").setTextAlignment(TextAlignment.CENTER));

        // Cerrar el documento
        document.close();
    }

}
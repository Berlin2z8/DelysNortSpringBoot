package com.marxchipana.DelysNortSpringBoot.controller;

import com.marxchipana.DelysNortSpringBoot.DAO.VentaRepository;
import com.marxchipana.DelysNortSpringBoot.models.Producto;
import com.marxchipana.DelysNortSpringBoot.models.Rol;
import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import com.marxchipana.DelysNortSpringBoot.models.Venta;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryProducto;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryRol;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryUsuario;
import com.marxchipana.DelysNortSpringBoot.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private RepositoryUsuario usuarioRepository;
    @Autowired
    private RepositoryRol rolRepository;
    @Autowired
    private RepositoryProducto productoRepository;
    @Autowired
    private VentaRepository ventaRepository;
    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Rol> roles = rolRepository.findAll();  // Carga los roles disponibles en la base de datos

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);
        return "admin";  // Renderiza la vista admin.html
    }
    @Autowired
    private PDFGeneratorService pdfGeneratorService;

    @GetMapping("/admin/pdf")
    public ResponseEntity<InputStreamResource> downloadUserReport() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        ByteArrayInputStream pdfStream = pdfGeneratorService.generateUserReport(usuarios);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=usuarios.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
    @Autowired
    private ExcelGeneratorService excelGeneratorService;

    @GetMapping("/admin/excel")
    public ResponseEntity<InputStreamResource> downloadUserExcelReport() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        ByteArrayInputStream excelStream = excelGeneratorService.generateUserExcelReport(usuarios);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=usuarios.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Tipo de contenido para descarga
                .body(new InputStreamResource(excelStream));
    }

    @GetMapping("/admin/productos")
    public String productos(Model model) {
        List<Producto> productos = productoRepository.findAll(); // Obtén todos los productos
        model.addAttribute("productos", productos); // Agrega la lista al modelo
        return "adminproductos"; // Nombre de la vista que va a retornar, que es adminproductos.html
    }

    @Autowired
    private PDFGeneratorProductosService pdfGeneratorProductosService;

    @GetMapping("/admin/productos/pdf")
    public ResponseEntity<InputStreamResource> downloadProductReport() {
        // Obtener la lista de productos
        List<Producto> productos = productoRepository.findAll();

        // Generar el PDF del reporte de productos
        ByteArrayInputStream pdfStream = pdfGeneratorProductosService.generateProductReport(productos);

        // Configurar los headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=productos.pdf");

        // Retornar la respuesta con el PDF
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    @Autowired
    private ExcelGeneratorProductosService excelGeneratorProductosService;

    @GetMapping("/admin/productos/excel")
    public ResponseEntity<InputStreamResource> downloadProductReportExcel() {
        List<Producto> productos = productoRepository.findAll();
        ByteArrayInputStream excelStream = excelGeneratorProductosService.generateProductReportExcel(productos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=productos.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(excelStream));
    }

    @PostMapping("/admin/productos/crear")
    public String crearProducto(@RequestParam("nombre") String nombre,
                                @RequestParam("descripcion") String descripcion,
                                @RequestParam("precio") BigDecimal precio,
                                @RequestParam("imagen") MultipartFile imagen) {
        // Crear una nueva instancia de Producto
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(nombre);
        nuevoProducto.setDescripcion(descripcion);
        nuevoProducto.setPrecio(precio);

        // Usar una ruta relativa para guardar la imagen
        String imagenPath = new File("src/main/resources/static/images/productos/").getAbsolutePath();
        String nombreImagen = imagen.getOriginalFilename();

        try {
            // Crear la carpeta si no existe
            File directorio = new File(imagenPath);
            if (!directorio.exists()) {
                directorio.mkdirs(); // Crea las carpetas necesarias
            }

            // Transferir la imagen
            File destino = new File(directorio, nombreImagen);
            imagen.transferTo(destino);
            nuevoProducto.setImagen("/images/productos/" + nombreImagen); // Guarda la ruta relativa
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
            // Manejo adicional de errores (puedes redirigir o mostrar un mensaje de error)
        }

        // Guardar el nuevo producto en la base de datos
        productoRepository.save(nuevoProducto);

        return "redirect:/admin/productos"; // Redirigir a la lista de productos
    }

    @GetMapping("/admin/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoRepository.deleteById(id);
        return "redirect:/admin/productos";
    }
    @PostMapping("/admin/productos/editar")
    public String editarProducto(@RequestParam("id") Integer id,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("descripcion") String descripcion,
                                 @RequestParam("precio") BigDecimal precio,
                                 @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Update existing fields
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);

        // Handle new image upload
        if (imagen != null && !imagen.isEmpty()) {
            // Usar una ruta relativa para guardar la imagen
            String imagenPath = new File("src/main/resources/static/images/productos/").getAbsolutePath();
            String nombreImagen = imagen.getOriginalFilename();

            try {
                // Crear la carpeta si no existe
                File directorio = new File(imagenPath);
                if (!directorio.exists()) {
                    directorio.mkdirs(); // Crea las carpetas necesarias
                }

                // Transferir la imagen
                File destino = new File(directorio, nombreImagen);
                imagen.transferTo(destino);
                producto.setImagen("/images/productos/" + nombreImagen); // Guarda la ruta relativa
            } catch (IOException e) {
                e.printStackTrace(); // Manejo de errores
            }
        }

        // Guardar los cambios
        productoRepository.save(producto);
        return "redirect:/admin/productos"; // Redirigir a la lista de productos
    }

    @PostMapping("/admin/editar")
    public String editarUsuario(@ModelAttribute Usuario usuario) {
        usuarioRepository.save(usuario);  // Guardar los cambios
        return "redirect:/admin";
    }

    //Para eliminar usuario en la tabla de admin.html
    @GetMapping("/admin/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/ventas")
    public String ventas(Model model) {
        List<Venta> venta = ventaRepository.findAll(); // Obtén todos las ventas
        model.addAttribute("ventas", venta); // Agrega la lista al modelo
        return "adminVentas"; // Nombre de la vista que va a retornar, que es adminVentas.html
    }

    @PostMapping("/admin/ventas/editar")
    public String editarVenta(@ModelAttribute Venta venta) {
        ventaRepository.save(venta);  // Guardar los cambios
        return "redirect:/admin/ventas";
    }

    @GetMapping("/admin/ventas/eliminar/{id}")
    public String eliminarVenta(@PathVariable Integer id) {
        ventaRepository.deleteById(id);
        return "redirect:/admin/ventas";
    }
    @Autowired
    private PDFGeneratorVentasService pdfGeneratorVentasService;
    @GetMapping("/admin/ventas/pdf")
    public ResponseEntity<InputStreamResource> downloadSalesReport() {
        List<Venta> ventas = ventaRepository.findAll();
        ByteArrayInputStream pdfStream = pdfGeneratorVentasService.generateSalesReport(ventas);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=ventas_reporte.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Lógica para cerrar sesión, el boton que hay en el nav
        // Redireccionar al login
        return "redirect:/login"; // Redirige al login después de cerrar sesión
    }
}

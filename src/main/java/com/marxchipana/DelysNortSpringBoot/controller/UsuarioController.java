package com.marxchipana.DelysNortSpringBoot.controller;

import com.marxchipana.DelysNortSpringBoot.DAO.CarritoRepository;
import com.marxchipana.DelysNortSpringBoot.DAO.VentaRepository;
import com.marxchipana.DelysNortSpringBoot.models.Carrito;
import com.marxchipana.DelysNortSpringBoot.models.Producto;
import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import com.marxchipana.DelysNortSpringBoot.models.Venta;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryProducto;
import com.marxchipana.DelysNortSpringBoot.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
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

    @GetMapping("/cliente")
    public String usuarioPage(Model model) {
        var productos = productoRepository.findAll();
        var carrito = carritoRepository.findAll();

        var totalCarrito = carrito.stream()
                .map(item -> item.getProducto().getPrecio().multiply(
                        BigDecimal.valueOf(item.getCantidad())
                ))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("productos", productos);
        model.addAttribute("carrito", carrito);
        model.addAttribute("cantidadCarrito", carrito.size());
        model.addAttribute("totalCarrito", totalCarrito);

        return "dashboard";
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
        Usuario usuario = usuarioService.findByEmail(principal.getName());

        // Si se pasa un producto específico
        List<Producto> productos;
        if (productoId != null) {
            Producto producto = productoRepository.findById(productoId).orElseThrow();
            productos = List.of(producto); // Lista con un solo producto
        } else {
            // Mostrar todos los productos del carrito ya agregado en /cliente
            productos = carritoRepository.findAll().stream()
                    .map(Carrito::getProducto)
                    .toList();
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("productos", productos); // Agregamos productos al modelo
        model.addAttribute("venta", new Venta()); // Objeto Venta vacío

        return "formularioVenta";
    }


    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute Venta venta, Principal principal) {
        // Asignar el usuario autenticado a la venta
        Usuario usuario = usuarioService.findByEmail(principal.getName());
        venta.setUsuario(usuario);
        ventaRepository.save(venta); // Guardar la venta
        return "redirect:/cliente";
    }

    private List<Carrito> carrito;

    @DeleteMapping("/carrito/eliminar/{id}")
    public String eliminarProductoDelCarrito(@PathVariable("id") Integer id) {
        carritoRepository.deleteById(id);  // Eliminar de la base de datos, del carrito que hay en el nav
        return "redirect:/cliente";  // Redirigir a la página del cliente para actualizar la vista, lo puedes cambiar si quieres otro apartado solo para productos
    }

}

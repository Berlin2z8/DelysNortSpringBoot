package com.marxchipana.DelysNortSpringBoot.controller;

import com.marxchipana.DelysNortSpringBoot.DAO.CarritoRepository;
import com.marxchipana.DelysNortSpringBoot.DAO.VentaRepository;
import com.marxchipana.DelysNortSpringBoot.models.*;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryProducto;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryRol;
import com.marxchipana.DelysNortSpringBoot.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            Principal principal) {

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

        return "redirect:/cliente"; // Redireccionar a la página del cliente
    }

    private List<Carrito> carrito;

    @DeleteMapping("/carrito/eliminar/{id}")
    public String eliminarProductoDelCarrito(@PathVariable("id") Integer id) {
        carritoRepository.deleteById(id);  // Eliminar de la base de datos, del carrito que hay en el nav
        return "redirect:/cliente";  // Redirigir a la página del cliente para actualizar la vista, lo puedes cambiar si quieres otro apartado solo para productos
    }

}

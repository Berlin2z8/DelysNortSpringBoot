package com.marxchipana.DelysNortSpringBoot.controller;

import com.marxchipana.DelysNortSpringBoot.models.Producto;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryProducto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoApiController {

    private final RepositoryProducto productoRepository;

    public ProductoApiController(RepositoryProducto productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping("/api/producto/{id}")
    public Producto obtenerProductoPorId(@PathVariable Integer id) {
        return productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }
}
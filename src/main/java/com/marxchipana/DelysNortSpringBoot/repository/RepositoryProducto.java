package com.marxchipana.DelysNortSpringBoot.repository;

import com.marxchipana.DelysNortSpringBoot.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RepositoryProducto extends JpaRepository<Producto, Integer> {
    // Método para encontrar un producto por su nombre
    Optional<Producto> findByNombre(String nombre);

    // Método para buscar productos que contengan una cadena en el nombre
    List<Producto> findByNombreContaining(String nombreParcial);

    // Método para buscar productos por precio
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
}

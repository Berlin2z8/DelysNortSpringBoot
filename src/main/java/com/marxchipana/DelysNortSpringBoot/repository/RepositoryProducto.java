package com.marxchipana.DelysNortSpringBoot.repository;

import com.marxchipana.DelysNortSpringBoot.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryProducto extends JpaRepository<Producto, Integer> {
}

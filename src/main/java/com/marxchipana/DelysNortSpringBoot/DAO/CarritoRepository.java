package com.marxchipana.DelysNortSpringBoot.DAO;

import com.marxchipana.DelysNortSpringBoot.models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
}

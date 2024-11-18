package com.marxchipana.DelysNortSpringBoot.DAO;

import com.marxchipana.DelysNortSpringBoot.models.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}

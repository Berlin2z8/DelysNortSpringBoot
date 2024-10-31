package com.marxchipana.DelysNortSpringBoot.repository;

import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUsuario extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
}

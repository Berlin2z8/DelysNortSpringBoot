package com.marxchipana.DelysNortSpringBoot.repository;

import com.marxchipana.DelysNortSpringBoot.models.Rol;
import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositoryRol extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
    // Nuevo método para guardar usuarios
    void save(Usuario usuario);

}

package com.marxchipana.DelysNortSpringBoot.services;

import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private RepositoryUsuario usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

}
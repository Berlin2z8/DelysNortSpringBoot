package com.marxchipana.DelysNortSpringBoot.services;

import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private RepositoryUsuario usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyección de BCryptPasswordEncoder

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    @Override
    public void actualizarContrasena(Usuario usuario, String nuevaContrasena) {
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));  // Encriptar la nueva contraseña
        usuarioRepository.save(usuario);  // Guardar el usuario con la nueva contraseña
    }

}
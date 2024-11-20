package com.marxchipana.DelysNortSpringBoot.services;

import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import com.marxchipana.DelysNortSpringBoot.repository.RepositoryUsuario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordEncryptorService {

    @Autowired
    private RepositoryUsuario usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void encryptExistingPasswords() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            // Verificar si la contraseña ya está encriptada
            if (!passwordEncoder.matches("dummyPassword", usuario.getPassword())) { // Cambia la lógica según sea necesario
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
        }
        usuarioRepository.saveAll(usuarios);
    }
}

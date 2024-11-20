package com.marxchipana.DelysNortSpringBoot.services;

import com.marxchipana.DelysNortSpringBoot.models.Usuario;

public interface UsuarioService {
    Usuario findByEmail(String email);
    void guardarUsuario(Usuario usuario); // Guardar un usuario
    void actualizarContrasena(Usuario usuario, String nuevaContrasena);  // Actualizar la contraseña

}
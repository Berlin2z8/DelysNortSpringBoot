package com.marxchipana.DelysNortSpringBoot.controller;

import com.marxchipana.DelysNortSpringBoot.models.Usuario;
import com.marxchipana.DelysNortSpringBoot.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String name = data.get("name");

        // Buscar el usuario por correo
        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario == null || !usuario.getNombre().equalsIgnoreCase(name)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Correo o nombre incorrectos"));
        }

        // Generar nueva contraseña
        String nuevaContrasena = generarNuevaContrasena();

        // Actualizar la contraseña en la base de datos
        usuarioService.actualizarContrasena(usuario, nuevaContrasena);

        // Retornar la nueva contraseña en la respuesta
        return ResponseEntity.ok(new ResponseMessage("success", nuevaContrasena));
    }

    private String generarNuevaContrasena() {
        // Aquí puedes usar cualquier lógica para generar la nueva contraseña
        return UUID.randomUUID().toString().substring(0, 8);  // Contraseña aleatoria de 8 caracteres
    }

    // Clase auxiliar para devolver la respuesta en JSON
    public static class ResponseMessage {
        private String status;
        private String newPassword;
        private String message;

        public ResponseMessage(String status) {
            this.status = status;
        }

        public ResponseMessage(String status, String newPassword) {
            this.status = status;
            this.newPassword = newPassword;
        }

        public String getStatus() {
            return status;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

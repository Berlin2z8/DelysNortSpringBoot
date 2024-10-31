package com.marxchipana.DelysNortSpringBoot.config;

import com.marxchipana.DelysNortSpringBoot.services.ClienteUsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Solo admin puede acceder a /admin
                        .requestMatchers("/dashboard").authenticated()  // Cualquier usuario autenticado puede entrar
                        .anyRequest().permitAll()  // Otros accesos permitidos
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Ruta del formulario de login
                        .defaultSuccessUrl("/redirect", true)  // Redirige tras login
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    // Evitamos cifrado para simplificar
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
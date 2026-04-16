package caso2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // 1. Rutas públicas (No requieren login)
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/webjars/**").permitAll()
                
                // 2. Seguridad de Usuarios y Roles (Solo ADMIN)
                .requestMatchers("/usuarios/**", "/roles/**").hasRole("ADMIN")
                
                // 3. Seguridad de Edición de Eventos (ADMIN y ORGANIZADOR)
                .requestMatchers("/eventos/nuevo", "/eventos/guardar", "/eventos/editar/**", "/eventos/eliminar/**").hasAnyRole("ADMIN", "ORGANIZADOR")
                
                // 4. Seguridad de Consulta y Catálogo (Todos los roles autenticados)
                .requestMatchers("/eventos/**", "/consultas/**").hasAnyRole("ADMIN", "ORGANIZADOR", "CLIENTE")
                
                // 5. Cualquier otra ruta requiere estar logueado
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // Redirige al Dashboard principal tras el éxito
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    // Nota: Se mantiene NoOp porque las contraseñas en la base de datos están en texto plano
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); 
    }
}
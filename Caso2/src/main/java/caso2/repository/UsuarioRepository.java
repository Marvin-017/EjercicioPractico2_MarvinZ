package caso2.repository;

import caso2.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Necesario para el Login de Spring Security
    Usuario findByEmail(String email); 
    
    // Consulta derivada: Buscar usuarios por el nombre de su rol
    List<Usuario> findByRolNombre(String nombreRol);
}
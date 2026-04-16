package caso2.repository;

import caso2.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    // Consultas derivadas requeridas por las instrucciones
    List<Evento> findByActivo(Boolean activo);
    
    List<Evento> findByFechaBetween(LocalDate inicio, LocalDate fin);
    
    List<Evento> findByNombreContainingIgnoreCase(String nombre);
    
    long countByActivo(Boolean activo);
}
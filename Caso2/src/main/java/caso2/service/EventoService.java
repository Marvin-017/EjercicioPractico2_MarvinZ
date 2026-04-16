package caso2.service;

import caso2.domain.Evento;
import caso2.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    // CRUD Básico
    public List<Evento> listarEventos() { return eventoRepository.findAll(); }
    public Evento guardar(Evento evento) { return eventoRepository.save(evento); }
    public Evento buscarPorId(Long id) { return eventoRepository.findById(id).orElse(null); }
    public void eliminar(Long id) { eventoRepository.deleteById(id); }

    // Consultas Derivadas (Requisito E)
    public List<Evento> buscarActivos() { 
        return eventoRepository.findByActivo(true); 
    }
    
    public List<Evento> buscarPorFechas(LocalDate inicio, LocalDate fin) { 
        return eventoRepository.findByFechaBetween(inicio, fin); 
    }
    
    public List<Evento> buscarPorNombre(String nombre) { 
        return eventoRepository.findByNombreContainingIgnoreCase(nombre); 
    }
    
    public long contarActivos() { 
        return eventoRepository.countByActivo(true); 
    }
}
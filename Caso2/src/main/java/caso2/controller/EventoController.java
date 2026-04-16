package caso2.controller;

import caso2.domain.Evento;
import caso2.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // MÉTODO LISTAR: Ahora solo muestra todos los eventos sin filtros de fecha
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("eventos", eventoService.listarEventos());
        model.addAttribute("eventoNuevo", new Evento());
        return "eventos";
    }

    @PostMapping("/guardar")
    public String guardar(Evento evento) {
        eventoService.guardar(evento);
        return "redirect:/eventos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
        return "redirect:/eventos";
    }

    // --- SECCIÓN DE CONSULTAS: Aquí se queda toda la lógica de filtros ---
    @GetMapping("/consultas")
    public String consultas(Model model, 
                           @RequestParam(required = false) String tipoConsulta, 
                           @RequestParam(required = false) String valor,
                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        
        if (tipoConsulta != null) {
            switch (tipoConsulta) {
                case "activos":
                    model.addAttribute("resultados", eventoService.buscarActivos());
                    model.addAttribute("mensaje", "Mostrando eventos activos.");
                    break;
                case "nombre":
                    model.addAttribute("resultados", eventoService.buscarPorNombre(valor));
                    model.addAttribute("mensaje", "Búsqueda por nombre: " + valor);
                    break;
                case "conteo":
                    model.addAttribute("mensaje", "Total de eventos activos: " + eventoService.contarActivos());
                    break;
                case "fechas":
                    if (inicio != null && fin != null) {
                        model.addAttribute("resultados", eventoService.buscarPorFechas(inicio, fin));
                        model.addAttribute("mensaje", "Eventos entre " + inicio + " y " + fin);
                    } else {
                        model.addAttribute("mensaje", "Debe seleccionar ambas fechas.");
                    }
                    break;
            }
        }
        return "consultas"; 
    }
    
    @GetMapping("/editar/{id}")
public String editar(@PathVariable Long id, Model model) {
    model.addAttribute("eventos", eventoService.listarEventos());
    model.addAttribute("eventoNuevo", eventoService.buscarPorId(id));
    return "eventos";
}
}
package caso2.controller;

import caso2.domain.Usuario;
import caso2.service.RolService;
import caso2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String rolNombre) {
        // Si el parámetro rolNombre trae algo, filtramos. Si no, listamos todos.
        if (rolNombre != null && !rolNombre.isEmpty()) {
            model.addAttribute("usuarios", usuarioService.buscarPorRol(rolNombre));
            model.addAttribute("filtroAplicado", rolNombre); // Para saber qué estamos filtrando
        } else {
            model.addAttribute("usuarios", usuarioService.listarUsuarios());
        }
        
        model.addAttribute("roles", rolService.listarRoles()); 
        model.addAttribute("usuarioNuevo", new Usuario());
        return "usuarios"; 
    }

    @PostMapping("/guardar")
    public String guardar(Usuario usuario) {
        // Determinamos si es nuevo verificando si tiene un ID asignado
        boolean esNuevo = (usuario.getId() == null || usuario.getId() == 0);
        usuarioService.guardar(usuario, esNuevo); // El servicio enviará el correo si es nuevo
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/usuarios";
    }

    // --- NUEVO MÉTODO PARA LA EDICIÓN ---
    // Usamos @ResponseBody para que devuelva el objeto Usuario en formato JSON
    @GetMapping("/editar/{id}")
    @ResponseBody
    public Usuario editar(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }
    
}
package caso2.controller;

import caso2.domain.Usuario;
import caso2.service.RolService;
import caso2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String rolNombre) {
        if (rolNombre != null && !rolNombre.isEmpty()) {
            model.addAttribute("usuarios", usuarioService.buscarPorRol(rolNombre));
            model.addAttribute("filtroAplicado", rolNombre);
        } else {
            model.addAttribute("usuarios", usuarioService.listarUsuarios());
        }

        model.addAttribute("roles", rolService.listarRoles());
        model.addAttribute("usuarioNuevo", new Usuario());
        return "usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(Usuario usuario) {
        boolean esNuevo = (usuario.getId() == null || usuario.getId() == 0);
        usuarioService.guardar(usuario, esNuevo);
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "No se puede eliminar el usuario. Fue desactivado correctamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Usuario editar(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }
}
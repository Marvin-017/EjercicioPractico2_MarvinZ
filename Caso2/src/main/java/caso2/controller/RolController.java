package caso2.controller;

import caso2.domain.Rol;
import caso2.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("roles", rolService.listarRoles());
        model.addAttribute("rolNuevo", new Rol());
        return "roles"; // Apunta a roles.html
    }

    @PostMapping("/guardar")
    public String guardar(Rol rol) {
        rolService.guardar(rol);
        return "redirect:/roles"; // Recarga la página después de guardar
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
        return "redirect:/roles";
    }
}
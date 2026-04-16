package caso2.controller;

import caso2.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalActivos", eventoService.contarActivos());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
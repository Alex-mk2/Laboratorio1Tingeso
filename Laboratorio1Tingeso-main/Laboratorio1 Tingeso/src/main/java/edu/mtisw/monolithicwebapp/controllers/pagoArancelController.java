package edu.mtisw.monolithicwebapp.controllers;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.services.pagoArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Controller
@RequestMapping
public class pagoArancelController {
    @Autowired
    pagoArancelService pagoArancelService;
    @Autowired
    edu.mtisw.monolithicwebapp.services.estudianteService estudianteService;

    @GetMapping("/calcular-arancel")
    public String crearPlanillaArancel(Model model) {
        boolean planillasGeneradas = pagoArancelService.calcularArancel();
        if (planillasGeneradas) {
            model.addAttribute("planillasGeneradas", true);
        }
        return "formulario-cuotas";
    }

    @GetMapping("/arancel")
    public String mostrarArancelEstudiante(Model model) {
        List<pagoArancelEntity> listaPagosArancel = pagoArancelService.listaArancel();
        model.addAttribute("pagosArancel", listaPagosArancel);
        return "arancel";
    }

}

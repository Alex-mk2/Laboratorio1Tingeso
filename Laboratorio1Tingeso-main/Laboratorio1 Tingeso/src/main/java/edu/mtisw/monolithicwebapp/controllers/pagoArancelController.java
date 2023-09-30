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

    @GetMapping("/formulario-cuotas")
    public String crearPlanillaArancel(Model model) {
        boolean planillasGeneradas = pagoArancelService.calcularArancel();
        if (planillasGeneradas) {
            model.addAttribute("planillasGeneradas", true);
        }
        return "formulario-cuotas";
    }

    @GetMapping("/mostrar-arancel")
    public String mostrarArancelEstudiante(Model model) {
        List<pagoArancelEntity> listaPagosArancel = pagoArancelService.listaArancel();
        model.addAttribute("pagosArancel", listaPagosArancel);
        return "arancel";
    }

    @GetMapping("/buscar-estado-estudiante")
    public String buscarEstadoEstudiante(@ModelAttribute("filtro") pagoArancelEntity filtro, Model model) {
        List<pagoArancelEntity> resultados = pagoArancelService.buscarEstadoEstudiante(filtro);
        model.addAttribute("resultados", resultados);
        return "mostrar-cuotas-pago";
    }
}


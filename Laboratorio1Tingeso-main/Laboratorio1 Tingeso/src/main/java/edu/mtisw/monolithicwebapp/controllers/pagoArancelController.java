package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.services.pagoArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class pagoArancelController {
    @Autowired
    pagoArancelService pagoArancelService;
    @Autowired
    edu.mtisw.monolithicwebapp.services.estudianteService estudianteService;

    @GetMapping("/cuotas/calcularTotal")
    public String mostrarFormulario(Model model){
        pagoArancelEntity cuotas = new pagoArancelEntity();
        estudianteEntity estudiante = new estudianteEntity();
        model.addAttribute("cuotas", cuotas);
        model.addAttribute("estudiante", estudiante);
        return "formulario-cuotas";
    }

    @PostMapping("/cuotas/calcularTotal")
    public String calcularTotalCuotas(@ModelAttribute("cuotas") pagoArancelEntity cuotas, @ModelAttribute("estudiante") estudianteEntity estudiante, Model model){
        double ArancelTotal = pagoArancelService.calcularArancelTotalEstudiante(estudiante, cuotas);
        model.addAttribute("ArancelTotal", ArancelTotal);
        return "arancel";
    }
}

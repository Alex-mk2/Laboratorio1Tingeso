package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudianteEntity;
import edu.mtisw.monolithicwebapp.services.CuotasService;
import edu.mtisw.monolithicwebapp.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CuotasController{
    @Autowired
    CuotasService cuotasService;
    @Autowired
    EstudianteService estudianteService;

    @GetMapping("/cuotas/calcularTotal")
    public String mostrarFormulario(Model model){
        CuotasEntity cuotas = new CuotasEntity();
        EstudianteEntity estudiante = new EstudianteEntity();
        model.addAttribute("cuotas", cuotas);
        model.addAttribute("estudiante", estudiante);
        return "formulario-cuotas";
    }

    @PostMapping("/cuotas/calcularTotal")
    public String calcularTotalCuotas(@ModelAttribute("cuotas") CuotasEntity cuotas, @ModelAttribute("estudiante") EstudianteEntity estudiante, Model model){
        double ArancelTotal = cuotasService.calcularArancelTotalEstudiante(estudiante, cuotas);
        model.addAttribute("ArancelTotal", ArancelTotal);
        return "arancel";
    }
}

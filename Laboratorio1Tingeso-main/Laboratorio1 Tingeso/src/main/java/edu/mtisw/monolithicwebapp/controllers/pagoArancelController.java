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
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class pagoArancelController {
    @Autowired
    pagoArancelService pagoArancelService;
    @Autowired
    edu.mtisw.monolithicwebapp.services.estudianteService estudianteService;

    @GetMapping("/Arancel-estudiante")
    public String mostrarFormulario(Model model){
        boolean mensaje = pagoArancelService.calcularArancel();
        model.addAttribute("mensaje", mensaje);
        return "formulario-cuotas";
    }

    @PostMapping("/plantilla-arancel")
    public String calcularTotalCuotas(Model model){

        return "arancel";
    }
}

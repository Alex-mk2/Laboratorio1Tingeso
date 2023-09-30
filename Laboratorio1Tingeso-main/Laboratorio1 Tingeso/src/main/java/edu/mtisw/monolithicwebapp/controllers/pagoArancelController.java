package edu.mtisw.monolithicwebapp.controllers;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.services.pagoArancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
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

    @GetMapping("/mostrar-cuotas-pago/{id_estudiante}")

    public String mostrarCuotasDePago(@PathVariable String rut, @PathVariable Long idEstudiante, Model model){
        estudianteEntity estudiante = estudianteService.findByIdEstudiante(idEstudiante);
        if(estudiante!= null){
            List<pagoArancelEntity> cuotasPago = pagoArancelService.buscarListaEstudiantePorRut(rut);
            model.addAttribute("estudiante", estudiante);
            model.addAttribute("cuotasPago", cuotasPago);
            return "mostrar-cuotas-pago";
        }else{
            return "error";
        }
    }
}

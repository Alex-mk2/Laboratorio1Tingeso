package edu.mtisw.monolithicwebapp.controllers;
import edu.mtisw.monolithicwebapp.entities.PruebaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import edu.mtisw.monolithicwebapp.services.PruebaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;


@Controller
@RequestMapping
public class PruebaController{

    @Autowired
    private PruebaService pruebaService;

    @GetMapping("/uploadData")
    public String uploadData(){
        return "uploadData";
    }
    @PostMapping("/uploadData")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        pruebaService.GuardarNombreArchivo(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        pruebaService.LeerArchivoCsv("Prueba.csv");
        return "redirect:/fileUpload";
    }

    @GetMapping("/VisualizarPruebas")
    public String mostrarPruebas(Model model){
        ArrayList<PruebaEntity> Pruebas = pruebaService.ObtenerDatosPruebas();
        model.addAttribute("pruebas", Pruebas);
        return "mostrar-pruebas";
    }
}


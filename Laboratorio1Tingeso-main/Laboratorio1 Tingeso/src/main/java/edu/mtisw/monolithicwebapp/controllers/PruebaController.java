package edu.mtisw.monolithicwebapp.controllers;
import edu.mtisw.monolithicwebapp.entities.PruebaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import edu.mtisw.monolithicwebapp.services.PruebaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.util.ArrayList;


@Controller
@RestController
public class PruebaController{

    @Autowired
    private PruebaService pruebaService;

    @GetMapping("/uploadData")
    public String uploadData(){
        return "updateTest";
    }
    @PostMapping("/uploadData")
    public String uploadData(@RequestParam("file") MultipartFile file) {
        try {
            pruebaService.GuardarNombreArchivo(file);
            pruebaService.LeerArchivoCsv("Pruebas.csv");
            return "Archivo cargado con éxito";
        } catch (Exception e) {
            return "Error al procesar el archivo: " + e.getMessage();
        }
    }

    @GetMapping("/VisualizarPruebas")
    public String mostrarPruebas(Model model){
        ArrayList<PruebaEntity> Pruebas = pruebaService.ObtenerDatosPruebas();
        model.addAttribute("pruebas", Pruebas);
        return "mostrar-pruebas";
    }
}


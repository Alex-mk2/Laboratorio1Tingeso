package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.EstudianteEntity;
import edu.mtisw.monolithicwebapp.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class EstudianteController {
    @Autowired
	EstudianteService estudianteService;
    @GetMapping("/MostrarEstudiante")
	public String MostrarEstudiante(Model model) {
    	ArrayList<EstudianteEntity> Estudiante= estudianteService.obtenerEstudiante();
    	model.addAttribute("estudiante",Estudiante);
		return "Estudiante";
	}

	@GetMapping("/AgregarEstudiante")
	public String AgregarEstudiante(EstudianteEntity NuevoEstudiante){
		return "Agregar";
	}

	@PostMapping("/guardarEstudiante")
	public String guardarEstudiante(EstudianteEntity NuevoEstudiante){
		estudianteService.guardarEstudiante(NuevoEstudiante);
		return "redirect:/";
	}
}
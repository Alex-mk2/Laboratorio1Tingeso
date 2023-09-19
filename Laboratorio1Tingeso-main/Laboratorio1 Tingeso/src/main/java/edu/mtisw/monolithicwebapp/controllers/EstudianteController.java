package edu.mtisw.monolithicwebapp.controllers;
import edu.mtisw.monolithicwebapp.entities.EstudianteEntity;
import edu.mtisw.monolithicwebapp.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;

@Controller
@RequestMapping
public class EstudianteController {
	@Autowired
	EstudianteService estudianteService;
	@GetMapping("/MostrarEstudiante/listar")
	public String MostrarEstudiante(Model model) {
		ArrayList<EstudianteEntity> estudiante= estudianteService.obtenerEstudiante();
		model.addAttribute("estudiante",estudiante);
		return "Lista-Estudiantes";
	}
	@GetMapping("/estudiante/agregar")
	public String guardarEstudiante(Model model){
		model.addAttribute("estudiante", new EstudianteEntity());
		return "nuevo-estudiante";
	}

	@PostMapping(value = "estudiante/save")
	public String guardarEstudiante(@ModelAttribute("estudiante") EstudianteEntity estudianteEntity){
		estudianteService.guardarEstudiante(estudianteEntity);
		return "redirect:/";
	}
}
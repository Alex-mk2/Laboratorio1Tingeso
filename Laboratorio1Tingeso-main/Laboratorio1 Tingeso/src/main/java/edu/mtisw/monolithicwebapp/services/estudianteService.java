package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import edu.mtisw.monolithicwebapp.repositories.estudianteRepository;


@Service
public class estudianteService{

    @Autowired
    estudianteRepository estudianteRepository;

    public estudianteService(estudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }


    public ArrayList<estudianteEntity> obtenerEstudiante(){
        return (ArrayList<estudianteEntity>) estudianteRepository.findAll();
    }

    public estudianteEntity guardarEstudiante(estudianteEntity NuevoEstudiante){
        return estudianteRepository.save(NuevoEstudiante);
    }

    public estudianteEntity findByIdEstudiante(Long id_estudiante){
        return estudianteRepository.findByIdEstudiante(id_estudiante);
    }
}
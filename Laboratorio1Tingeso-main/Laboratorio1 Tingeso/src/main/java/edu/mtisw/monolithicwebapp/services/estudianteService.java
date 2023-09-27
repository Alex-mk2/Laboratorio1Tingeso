package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class estudianteService {
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.estudianteRepository estudianteRepository;
    
    public ArrayList<estudianteEntity> obtenerEstudiante(){
        return (ArrayList<estudianteEntity>) estudianteRepository.findAll();
    }

    public estudianteEntity guardarEstudiante(estudianteEntity NuevoEstudiante){
        return estudianteRepository.save(NuevoEstudiante);
    }

    public Optional<estudianteEntity> obtenerPorId(Long id){
        return estudianteRepository.findById(id);
    }

    public boolean eliminarUsuario(Long id) {
        try{
            estudianteRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
    public estudianteEntity findByRut(String rut){
        return estudianteRepository.findEstudianteByRut(rut);
    }
}
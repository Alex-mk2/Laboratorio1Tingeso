package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class estudianteService{

    @Autowired
    edu.mtisw.monolithicwebapp.repositories.estudianteRepository estudianteRepository;

    public ArrayList<estudianteEntity> obtenerEstudiante(){
        return (ArrayList<estudianteEntity>) estudianteRepository.findAll();
    }

    public estudianteEntity guardarEstudiante(estudianteEntity NuevoEstudiante){
        return estudianteRepository.save(NuevoEstudiante);
    }

    public estudianteEntity findByRut(String rut){
        return estudianteRepository.findEstudianteByRut(rut);
    }

    public List<estudianteEntity> listaEstudiantes(){
        return estudianteRepository.findAll();
    }

    public estudianteEntity obtenerAnioEgreso(int egreso){
        return estudianteRepository.findEstudianteByEgreso(egreso);
    }

    public List<estudianteEntity> obtenerListaEgreso(int egreso){
        return estudianteRepository.findListByEgreso(egreso);
    }

    public estudianteEntity findByIdEstudiante(Long id_estudiante){
        return estudianteRepository.findByIdEstudiante(id_estudiante);
    }
}
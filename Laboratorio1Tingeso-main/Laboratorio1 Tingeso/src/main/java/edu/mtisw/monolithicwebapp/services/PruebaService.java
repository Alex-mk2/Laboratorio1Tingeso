package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.PruebaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PruebaService{
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.pruebaRepository pruebaRepository;
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.estudianteRepository estudianteRepository;
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository pagoArancelRepository;

    public void guardarPruebaEstudiante(PruebaEntity prueba){
        pruebaRepository.save(prueba);
    }

    public double calcularPruebaPromedio(List<PruebaEntity> pruebas){
        int puntajeObtenido = 0;
        if(pruebas.isEmpty()){
            return 0.0;
        }else{
            for (PruebaEntity prueba : pruebas){
                puntajeObtenido += prueba.getPuntaje_obtenido();
            }
        }
        return (double) puntajeObtenido/pruebas.size();
    }
}

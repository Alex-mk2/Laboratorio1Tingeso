package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudianteEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import edu.mtisw.monolithicwebapp.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CuotasService {
    @Autowired
    CuotasRepository cuotasRepository;
    @Autowired
    EstudianteRepository estudianteRepository;

    public double calcularDescuentoTipoEstablecimiento(String tipo_establecimiento){
        if("Municipal".equals(tipo_establecimiento)){
            return 0.2;
        } else if ("Subvencionado".equals(tipo_establecimiento)){
            return 0.1;
        }else{
            return 0.0;
        }
    }

    public double calcularDescuentoPorEgreso(int egreso){
        if(egreso < 1){
            return 0.15;
        } else if (egreso <= 2) {
            return 0.08;
        } else if (egreso <= 4){
            return 0.04;
        }else{
            return 0.0;
        }
    }

    public double calcularDescuentoPorPuntaje(int puntaje){
        if(puntaje >= 950 && puntaje <= 1000){
            return 0.1;
        } else if (puntaje>=900 && puntaje <= 949){
            return 0.05;
        } else if (puntaje >= 850 && puntaje <= 899){
            return 0.02;
        }else{
            return 0.0;
        }
    }
}

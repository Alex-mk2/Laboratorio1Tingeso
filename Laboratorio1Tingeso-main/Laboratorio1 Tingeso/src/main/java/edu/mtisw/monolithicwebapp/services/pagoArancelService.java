package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class pagoArancelService {
    @Autowired
    pagoArancelRepository pagoArancelRepository;
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.estudianteRepository estudianteRepository;

    private static final Double Arancel = 1500000.0;
    private static final Double Matricula = 70000.0;

    public void guardarCuotas(pagoArancelEntity cuotas){
        pagoArancelRepository.save(cuotas);
    }

    //Dom: Estudiante entity, aqui utilizamos el estudiante como base
    //Rec: cuotas perteneciente a cuotasEntity
    public pagoArancelEntity crearPlanillaEstudiante(estudianteEntity estudiante){
        pagoArancelEntity pagoArancel = new pagoArancelEntity();
        pagoArancel.setEstudiante(estudiante);
        int numeroCuotasEstablecimiento = cantidadCuotasEstablecimiento(estudiante);
        pagoArancel.setNumeroTotalCuotasPactadas(numeroCuotasEstablecimiento);
        double descuentoArancel = descuentoPorTipoProcedencia(estudiante, pagoArancel.getTipoPago()) + descuentoPorEgreso(estudiante);
        pagoArancel.setSaldoPorPagar(descuentoArancel);
        pagoArancel.setMontoTotalArancel(Arancel + Matricula);
        return pagoArancel;
    }

    public double descuentoPorTipoProcedencia(estudianteEntity estudiante, String tipoPago){
        String opcionPago = estudiante.getTipo_establecimiento();
        double descuentoTotal = 0.0;
        double montoTotal = Arancel + Matricula;
        if("Contado".equals(tipoPago)){
            descuentoTotal = montoTotal * 0.5;
        }
        if("Cuotas".equals(tipoPago)){
            if("Municipal".equals(opcionPago)){
                descuentoTotal = montoTotal * 0.2;
            } else if ("Subvencionado".equals(opcionPago)){
                descuentoTotal = montoTotal * 0.1;
            } else if ("Privado".equals(opcionPago)){
                descuentoTotal = montoTotal;
            }
        }
        return descuentoTotal;
    }

    public int cantidadCuotasEstablecimiento(estudianteEntity estudiante){
        int cantidadCuotas;
        if("Municipal".equals(estudiante.getTipo_establecimiento())){
            cantidadCuotas = 10;
        } else if ("Subvencionado".equals(estudiante.getTipo_establecimiento())){
            cantidadCuotas = 7;
        } else if ("Privado".equals(estudiante.getTipo_establecimiento())){
            cantidadCuotas = 4;
        }else{
            cantidadCuotas = 0;
        }
        return cantidadCuotas;
    }

    public double descuentoPorEgreso(estudianteEntity estudiante){
        double montoTotal = Arancel + Matricula;
        double descuentoTotal = 0.0;
        int anioEgreso = estudiante.getEgreso();
        int anioActual = LocalDate.now().getYear();
        int diferenciaAniosEgreso = anioActual - anioEgreso;
        if(diferenciaAniosEgreso < 1){
            descuentoTotal = montoTotal * 0.15;
        } else if (diferenciaAniosEgreso >= 1 && diferenciaAniosEgreso <= 2){
            descuentoTotal = montoTotal * 0.08;
        } else if (diferenciaAniosEgreso >= 3 && diferenciaAniosEgreso <= 4){
            descuentoTotal = montoTotal * 0.04;
        } else if (diferenciaAniosEgreso > 5){
            descuentoTotal = 0.0;
        }
        return descuentoTotal;
    }
}
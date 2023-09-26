package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudianteEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Calendar;


@Service
public class CuotasService{
    @Autowired
    CuotasRepository cuotasRepository;

    public void guardarCuotas(CuotasEntity cuotas){
        cuotasRepository.save(cuotas);
    }


    public double opcionPagoArancelEstudiante(EstudianteEntity estudiante, CuotasEntity cuotas){
        double montoTotal = 0.0;
        if("Contado".equals(cuotas.getTipoPago())){
            montoTotal+= cuotas.getMontoTotalArancel() * 0.5;
        }
        if("Cuotas".equals(cuotas.getTipoPago())){
            if("Municipal".equals(estudiante.getTipo_establecimiento())){
                montoTotal+= cuotas.getMontoTotalArancel() * 0.2;
            } else if ("Subvencionado".equals(estudiante.getTipo_establecimiento())){
                montoTotal+= cuotas.getMontoTotalArancel() * 0.1;
            } else if ("Privado".equals(estudiante.getTipo_establecimiento())){
                montoTotal = 0.0;
            }
        }
        return montoTotal;
    }

    public double calcularPorTiempoEgreso(EstudianteEntity estudiante, CuotasEntity cuotas){
        double montoArancel = 0.0;
        int aniosActual = Calendar.getInstance().get(Calendar.YEAR);
        int aniosDesdeEgreso = aniosActual - estudiante.getEgreso();
        if (aniosDesdeEgreso < 1){
            montoArancel += 0.15 * cuotas.getMontoTotalArancel();
        }else if (aniosDesdeEgreso <= 2){
            montoArancel += 0.08 * cuotas.getMontoTotalArancel();
        }else if (aniosDesdeEgreso <= 4){
            montoArancel += 0.04 * cuotas.getMontoTotalArancel();
        }else if (aniosDesdeEgreso > 5){
            montoArancel += cuotas.getMontoTotalArancel();
        }
        return montoArancel;
    }

    public int cantidadCuotasEstablecimiento(EstudianteEntity estudiante){
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

    public double promedioPruebasRendidas(CuotasEntity cuotas){
        if(cuotas.getPromedio_puntaje_examenes() >= 950 && cuotas.getPromedio_puntaje_examenes() < 1000){
            return 0.10;
        } else if (cuotas.getPromedio_puntaje_examenes() >= 900 && cuotas.getPromedio_puntaje_examenes() < 949){
            return 0.05;
        } else if (cuotas.getPromedio_puntaje_examenes() >= 850 && cuotas.getPromedio_puntaje_examenes() < 899) {
            return 0.02;
        } else if (cuotas.getPromedio_puntaje_examenes() < 850){
            return 0.0;
        }
        return 0;
    }

    public double calcularArancelTotalEstudiante(EstudianteEntity estudiante, CuotasEntity cuotas){
        double arancelTotal = 0.0;
        double opcionPago = 0.0;
        double tiempoEgreso = 0.0;
        tiempoEgreso+= calcularPorTiempoEgreso(estudiante, cuotas);
        opcionPago+= opcionPagoArancelEstudiante(estudiante, cuotas);
        arancelTotal+= tiempoEgreso + opcionPago;
        int cantidadCuotas = cantidadCuotasEstablecimiento(estudiante);
        if(cantidadCuotas > 0){
            arancelTotal*= cantidadCuotas/10.0;
        }
        return arancelTotal;
    }
}
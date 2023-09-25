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
}
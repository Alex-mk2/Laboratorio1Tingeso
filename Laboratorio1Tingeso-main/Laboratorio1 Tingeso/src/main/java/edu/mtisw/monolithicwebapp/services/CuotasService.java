package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudianteEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CuotasService{
    @Autowired
    CuotasRepository cuotasRepository;

    public void guardarCuotas(CuotasEntity cuotas){
        cuotasRepository.save(cuotas);
    }

    public double calcularTipoColegioProcedencia(List<EstudianteEntity> listaEstudiantes, CuotasEntity cuotas) {
        double montoArancel = 0.0;
        for (EstudianteEntity estudianteEntity : listaEstudiantes) {
            if ("Municipal".equals(estudianteEntity.getTipo_establecimiento())) {
                montoArancel += 0.2 * cuotas.getMontoTotalArancel();
            } else if ("Subvencionado".equals(estudianteEntity.getTipo_establecimiento())) {
                montoArancel += 0.1 * cuotas.getMontoTotalArancel();
            }
        }
        return montoArancel;
    }

    public double calcularPorTiempoEgreso(List<EstudianteEntity> listaEstudiantes, CuotasEntity cuotas){
        double montoArancel = 0.0;
        for(EstudianteEntity estudianteEntity : listaEstudiantes){
            if(estudianteEntity.getEgreso() < 1){
                montoArancel+= 0.15 * cuotas.getMontoTotalArancel();
            } else if (estudianteEntity.getEgreso() <= 2){
                montoArancel+= 0.08 * cuotas.getMontoTotalArancel();
            } else if (estudianteEntity.getEgreso() <= 4){
                montoArancel+= 0.02 * cuotas.getMontoTotalArancel();
            }
        }
        return montoArancel;
    }

    public void generarCuotasEstudiantes(List<EstudianteEntity> listaEstudiantes, CuotasEntity cuotas){
        for(EstudianteEntity estudianteEntity: listaEstudiantes){
            if("Municipal".equals(estudianteEntity.getTipo_establecimiento())){
                cuotas.setNumeroCuotasPactadas(10);
            } else if ("Subvencionado".equals(estudianteEntity.getTipo_establecimiento())){
                cuotas.setNumeroCuotasPactadas(7);
            } else if ("Privado".equals(estudianteEntity.getTipo_establecimiento())){
               cuotas.setNumeroCuotasPactadas(4);
            }
        }
    }
}
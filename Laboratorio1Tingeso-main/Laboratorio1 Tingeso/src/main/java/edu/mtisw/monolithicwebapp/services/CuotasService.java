package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import edu.mtisw.monolithicwebapp.entities.EstudianteEntity;
import edu.mtisw.monolithicwebapp.repositories.CuotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CuotasService{
    @Autowired
    CuotasRepository cuotasRepository;

    public void guardarCuotas(CuotasEntity cuotas){
        cuotasRepository.save(cuotas);
    }

    public double calcularTipoColegioProcedencia(EstudianteEntity estudiante, CuotasEntity cuotas) {
        double montoTotal = 0.0;
        switch (estudiante.getTipo_establecimiento()) {
            case "Municipal" -> montoTotal += 0.2 * cuotas.getMontoTotalArancel();
            case "Subvencionado" -> montoTotal += 0.1 * cuotas.getMontoTotalArancel();
            case "Privado" -> montoTotal += cuotas.getMontoTotalArancel();
        }
        return montoTotal;
    }

    public double calcularPorTiempoEgreso(EstudianteEntity estudiante, CuotasEntity cuotas){
        double montoArancel = 0.0;
        if(estudiante.getEgreso() < 1){
            montoArancel+= 0.15 * cuotas.getMontoTotalArancel();
        } else if (estudiante.getEgreso() <= 2){
            montoArancel+= 0.08 * cuotas.getMontoTotalArancel();
        } else if (estudiante.getEgreso() <= 4){
            montoArancel+= 0.04 * cuotas.getMontoTotalArancel();
        } else if (estudiante.getEgreso() > 5){
            montoArancel += cuotas.getMontoTotalArancel();
        }
        return montoArancel;
    }

    public double calcularMontoTotalAPagarCuotas(List<EstudianteEntity> listaEstudiantes, CuotasEntity cuotas) {
        double calcularCuotastotales = 0.0;
        for (EstudianteEntity estudianteEntity : listaEstudiantes) {
            double cuotaPorTiempoEgreso = calcularPorTiempoEgreso(estudianteEntity, cuotas);
            double cuotaPorTipoColegio = calcularTipoColegioProcedencia(estudianteEntity, cuotas);
            if (estudianteEntity.getTipo_establecimiento().equals("Municipal") ||
                    estudianteEntity.getTipo_establecimiento().equals("Subvencionado")) {
                calcularCuotastotales += cuotaPorTiempoEgreso + cuotaPorTipoColegio;
            } else if (estudianteEntity.getTipo_establecimiento().equals("Privado")) {
                calcularCuotastotales += cuotaPorTipoColegio + cuotaPorTiempoEgreso;
            }
        }
        return calcularCuotastotales;
    }

    public int cantidadCuotasDisponiblesEstudiantes(EstudianteEntity estudiante){
        return switch (estudiante.getTipo_establecimiento()) {
            case "Municipal" -> 10;
            case "Subvencionado" -> 7;
            case "Privado" -> 4;
            default -> 0;
        };
    }

    public List<LocalDate> calcularFechaVencimientoCuotas(CuotasEntity cuotas){
        List<LocalDate> fechaLimitePago = new ArrayList<>();
        String[] trozosAnios = cuotas.getFechaUltimoPago().split("-");
        int dia;
        int mes = Integer.parseInt(trozosAnios[0]);
        int anios = Integer.parseInt(trozosAnios[1]);
        int fechaLimiteInicio = 5;
        int fechaLimiteMaximo = 10;
        for(dia = fechaLimiteInicio; dia<= fechaLimiteMaximo; dia++){
            LocalDate fechaLimite = LocalDate.of(anios, mes, dia);
            fechaLimitePago.add(fechaLimite);
        }
        return fechaLimitePago;
    }

    public void crearModeloCuotasEstudiantes(List<EstudianteEntity> listaEstudiantes, CuotasEntity cuotas){
        for (EstudianteEntity estudianteEntity : listaEstudiantes){
            int cantidadCuotas = cantidadCuotasDisponiblesEstudiantes(estudianteEntity);
            double montoTotalAPagar = calcularMontoTotalAPagarCuotas(listaEstudiantes, cuotas);
            List<LocalDate> FechaVencimiento = calcularFechaVencimientoCuotas(cuotas);
        }
    }
}
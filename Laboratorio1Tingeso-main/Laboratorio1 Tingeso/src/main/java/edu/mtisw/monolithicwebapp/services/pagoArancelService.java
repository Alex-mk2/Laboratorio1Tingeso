package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Service
public class pagoArancelService {
    @Autowired
    pagoArancelRepository pagoArancelRepository;
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.estudianteRepository estudianteRepository;
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.pruebaRepository pruebaRepository;

    private static final Double Arancel = 1500000.0;

    public void guardarCuotas(pagoArancelEntity pagoArancel){
        pagoArancelRepository.save(pagoArancel);
    }

    public List<pagoArancelEntity> listaArancel(){
        return pagoArancelRepository.findAll();
    }

    public pagoArancelEntity buscarEstudiante(String rut){
        return pagoArancelRepository.findEstudianteByRut(rut);
    }

    public pagoArancelEntity verificarPlanilla(estudianteEntity estudiante){
       return pagoArancelRepository.findPlanillaByEstudiante(estudiante);
    }

    public pagoArancelEntity crearPlanillaEstudiante(estudianteEntity estudiante) {
        pagoArancelEntity pagoArancel = new pagoArancelEntity();
        pagoArancel.setEstudiante(estudiante);
        pagoArancel.setNombres(estudiante.getNombres());
        pagoArancel.setRut(estudiante.getRut());
        int numeroCuotasEstablecimiento = cantidadCuotasEstablecimiento(estudiante);
        double descuentoArancel = (descuentoPorTipoProcedencia(estudiante) + descuentoPorEgreso(estudiante));
        double pagoTotal = Arancel - descuentoArancel;
        double pagoPorCuota = pagoTotal / numeroCuotasEstablecimiento;
        pagoArancel.setMontoTotalArancel(pagoTotal);
        pagoArancel.setNumeroExamenesRendidos(0);
        pagoArancel.setPromedioPuntajeExamenes(0.0);
        pagoArancel.setNumeroTotalCuotasPactadas(numeroCuotasEstablecimiento);
        pagoArancel.setSaldoPorPagar(pagoPorCuota);
        pagoArancel.setFechaUltimoPago(LocalDate.now());
        pagoArancel.setPlazoPago(fechaPago());
        return pagoArancel;
    }

    public boolean calcularArancel() {
        List<estudianteEntity> listaEstudiante = estudianteRepository.findAll();
        boolean lecturaEstudiante = false;
        for (estudianteEntity estudiante : listaEstudiante) {
            if (estudiante != null) {
                pagoArancelEntity pagoArancel = crearPlanillaEstudiante(estudiante);
                pagoArancelRepository.save(pagoArancel);
                lecturaEstudiante = true;

            }
        }
        return lecturaEstudiante;
    }



    public double descuentoPorTipoProcedencia(estudianteEntity estudiante) {
        String opcionPago = estudiante.getTipo_establecimiento();
        double descuentoTotal = 0.0;
        if("Municipal".equals(opcionPago)) {
            descuentoTotal = 0.2;
        }else if("Subvencionado".equals(opcionPago)) {
            descuentoTotal = 0.1;
        }else if ("Privado".equals(opcionPago)) {
            descuentoTotal = 0;
        }
        return descuentoTotal * Arancel;
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

    public double descuentoPorEgreso(estudianteEntity estudiante) {
        double descuentoTotal = 0.0;
        int anioEgreso = estudiante.getEgreso();
        int anioActual = LocalDate.now().getYear();
        int diferenciaAniosEgreso = anioActual - anioEgreso;
        if (diferenciaAniosEgreso < 1) {
            descuentoTotal = 0.15;
        } else if (diferenciaAniosEgreso >= 1 && diferenciaAniosEgreso <= 2) {
            descuentoTotal = 0.08;
        } else if (diferenciaAniosEgreso >= 3 && diferenciaAniosEgreso <= 4) {
            descuentoTotal = 0.04;
        } else if (diferenciaAniosEgreso > 4) {
            descuentoTotal = 0.0;
        }
        return descuentoTotal * Arancel;
    }


    public LocalDate fechaPago(){
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaDePago = LocalDate.of(fechaActual.getYear(), fechaActual.getMonth(), 5);
        if (fechaActual.getDayOfMonth() > 10) {
            fechaDePago = fechaDePago.plus(1, ChronoUnit.MONTHS);
        }
        return fechaDePago;
    }

    public double descuentoPorPrueba(double promedioPuntaje){
        if(promedioPuntaje >= 950 && promedioPuntaje < 1000){
            return 0.10;
        } else if (promedioPuntaje >= 900 && promedioPuntaje < 949){
            return 0.05;
        } else if (promedioPuntaje >= 850 && promedioPuntaje < 899) {
            return 0.02;
        }else{
            return 0.0;
        }
    }

    public double atrasos() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaDePago = LocalDate.of(fechaActual.getYear(), fechaActual.getMonth(), 5);
        double descuento = 0.0;
        if (fechaActual.getDayOfMonth() > 10) {
            fechaDePago = fechaDePago.plus(1, ChronoUnit.MONTHS);
        }
        long mesesAtraso = ChronoUnit.MONTHS.between(fechaDePago, fechaActual);
        if(mesesAtraso == 1){
            descuento = 0.03;
        } else if (mesesAtraso ==  2){
            descuento = 0.06;
        } else if (mesesAtraso == 3){
            descuento = 0.09;
        } else if (descuento > 3){
            descuento = 0.15;
        }
        return descuento;
    }

}
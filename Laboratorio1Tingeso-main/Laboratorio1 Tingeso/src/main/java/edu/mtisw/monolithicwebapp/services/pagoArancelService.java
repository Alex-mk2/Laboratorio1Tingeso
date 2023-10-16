package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;
import edu.mtisw.monolithicwebapp.repositories.estudianteRepository;
import edu.mtisw.monolithicwebapp.repositories.pruebaRepository;



@Service
public class pagoArancelService{

    @Autowired
    pagoArancelRepository pagoArancelRepository;
    @Autowired
    estudianteRepository estudianteRepository;
    @Autowired
    pruebaRepository pruebaRepository;

    private static final Double Arancel = 1500000.0;

    public pagoArancelService(pagoArancelRepository pagoArancelRepository){
        this.pagoArancelRepository = pagoArancelRepository;
    }

    public void guardarArancel(pagoArancelEntity pagoArancel){
        pagoArancelRepository.save(pagoArancel);
    }

    public List<pagoArancelEntity> listaArancel(){
        return pagoArancelRepository.findAll();
    }

    public pagoArancelEntity buscarEstudiante(String rut){
        return pagoArancelRepository.findEstudianteByRut(rut);
    }

    public pagoArancelEntity buscarPlanillaEstudiante(estudianteEntity estudiante) {
        return pagoArancelRepository.findPlanillaByEstudiante(estudiante);
    }

    public pagoArancelEntity crearPlanillaEstudiante(estudianteEntity estudiante, String tipoPago) {
        pagoArancelEntity pagoArancel = new pagoArancelEntity();
        pagoArancel.setEstudiante(estudiante);
        pagoArancel.setNombres(estudiante.getNombres());
        pagoArancel.setRut(estudiante.getRut());
        if("Contado".equals(tipoPago)) {
            double montoContado = (1500000.0 / 2.0) - descuentoPorEgreso(estudiante);
            pagoArancel.setTipoPago("Contado");
            pagoArancel.setMontoTotalArancel(montoContado);
        } else {
            int numeroCuotasEstablecimiento = cantidadCuotasEstablecimiento(estudiante);
            int descuentoEgreso = descuentoPorEgreso(estudiante);
            double descuentoArancelProcedencia = descuentoPorTipoProcedencia(estudiante);
            double pagoTotal = Arancel - descuentoEgreso - descuentoArancelProcedencia;
            double pagoPorCuota = pagoTotal / numeroCuotasEstablecimiento;
            pagoArancel.setTipoPago("Cuotas");
            pagoArancel.setMontoTotalArancel(pagoTotal);
            pagoArancel.setNumeroTotalCuotasPactadas(numeroCuotasEstablecimiento);
            pagoArancel.setSaldoPorPagar(pagoPorCuota);
            pagoArancel.setNumeroCuotasConRetraso(0);
            pagoArancel.setNumeroCuotasPagadas(0);
            pagoArancel.setMontoTotalPagado(375000.0);
            pagoArancel.setFechaUltimoPago(LocalDate.now());
            pagoArancel.setPlazoPago(fechaPago());
        }

        return pagoArancel;
    }


    public boolean actualizarCuotaEstudiante() {
        List<estudianteEntity> listaEstudiantes = estudianteRepository.findAll();
        boolean registroEstudiante = false;
        for (estudianteEntity estudiante : listaEstudiantes) {
            if (estudiante != null) {
                pagoArancelEntity pagoCuota = buscarPlanillaEstudiante(estudiante);
                if (pagoCuota != null) {
                    double saldoPorPagar = pagoCuota.getSaldoPorPagar();
                    double montoTotalPagado = pagoCuota.getMontoTotalPagado();
                    LocalDate fechaPlazoPago = fechaPago();
                    if (saldoPorPagar > 0 && LocalDate.now().isAfter(fechaPlazoPago)) {
                        long mesesAtraso = ChronoUnit.MONTHS.between(fechaPlazoPago, LocalDate.now());

                    }
                    if(saldoPorPagar == montoTotalPagado) {
                        pagoCuota.setEstadoCuota("Pagado");
                        pagoCuota.setFechaUltimoPago(LocalDate.now());
                        pagoCuota.setNumeroCuotasPagadas(pagoCuota.getNumeroCuotasPagadas() + 1);
                        pagoCuota.setPlazoPago(fechaPago());
                        pagoCuota.setSaldoPorPagar(saldoPorPagar);
                    }else if (saldoPorPagar > 0) {
                        pagoCuota.setEstadoCuota("Pendiente");
                        pagoCuota.setPlazoPago(fechaPago());
                    }
                    pagoArancelRepository.save(pagoCuota);
                    registroEstudiante = true;
                }
            }
        }
        return registroEstudiante;
    }

    public boolean calcularArancel() {
        List<estudianteEntity> listaEstudiante = estudianteRepository.findAll();
        boolean lecturaEstudiante = false;
        for(estudianteEntity estudiante : listaEstudiante) {
            if (estudiante != null) {
                pagoArancelEntity pagoArancel = crearPlanillaEstudiante(estudiante, "Cuotas");
                guardarArancel(pagoArancel);
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



    public int descuentoPorEgreso(estudianteEntity estudiante) {
        int descuentoTotal = 0;
        int arancel = 1500000;
        String anioEgreso = estudiante.getEgreso();
        int anioConvertido = Integer.parseInt(anioEgreso);
        int anioActual = LocalDate.now().getYear();
        int diferenciaAniosEgreso = anioActual - anioConvertido;
        if (diferenciaAniosEgreso < 1) {
            descuentoTotal = 15;
        } else if (diferenciaAniosEgreso >= 1 && diferenciaAniosEgreso <= 2) {
            descuentoTotal = 8;
        } else if (diferenciaAniosEgreso >= 3 && diferenciaAniosEgreso <= 4) {
            descuentoTotal = 4;
        } else if (diferenciaAniosEgreso > 4) {
            descuentoTotal = 0;
        }

        int descuento = (descuentoTotal * arancel) / 100;
        return descuento;
    }

    public LocalDate fechaPago() {
        LocalDate fechaActual = LocalDate.now();
        int diaDePago = 5;
        if (fechaActual.getDayOfMonth() > 10) {
            fechaActual = fechaActual.plusMonths(1);
        }
        return fechaActual.withDayOfMonth(diaDePago);
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

    public double atrasos(long mesesAtraso) {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaDePago = LocalDate.of(fechaActual.getYear(), fechaActual.getMonth(), 5);
        double descuento = 0.0;
        if (fechaActual.getDayOfMonth() > 10) {
            fechaDePago = fechaDePago.plus(1, ChronoUnit.MONTHS);
        }
        mesesAtraso = ChronoUnit.MONTHS.between(fechaDePago, fechaActual);
        if(mesesAtraso == 1) {
            descuento = 0.03;
        }else if (mesesAtraso == 2) {
            descuento = 0.06;
        }else if (mesesAtraso == 3) {
            descuento = 0.09;
        }else if (mesesAtraso > 3) {
            descuento = 0.15;
        }
        return descuento;
    }
}
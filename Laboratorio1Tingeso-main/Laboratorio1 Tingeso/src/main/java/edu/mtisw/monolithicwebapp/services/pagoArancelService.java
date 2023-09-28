package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


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

    public pagoArancelEntity crearPlanillaEstudiante(estudianteEntity estudiante) {
        pagoArancelEntity pagoArancel = new pagoArancelEntity();
        pagoArancel.setEstudiante(estudiante);
        int numeroCuotasEstablecimiento = cantidadCuotasEstablecimiento(estudiante);
        pagoArancel.setNumeroTotalCuotasPactadas(numeroCuotasEstablecimiento);
        double descuentoArancel = descuentoPorTipoProcedencia(estudiante, pagoArancel.getTipoPago()) + descuentoPorEgreso(estudiante);
        double pagoTotal = Arancel - descuentoArancel;
        double pagoPorCuota = pagoTotal / numeroCuotasEstablecimiento;
        pagoArancel.setSaldoPorPagar(pagoPorCuota);
        pagoArancel.setMontoTotalArancel(pagoTotal);
        pagoArancel.setNombres(estudiante.getNombres());
        pagoArancel.setRut(estudiante.getRut());
        pagoArancel.setFechaUltimoPago(LocalDate.now());
        pagoArancel.setTipoPago(pagoArancel.getTipoPago());
        System.out.println("Descuento Arancel: " + descuentoArancel);
        System.out.println("Pago Total: " + pagoTotal);
        System.out.println("Pago por Cuota: " + pagoPorCuota);
        return pagoArancel;
    }


    public boolean calcularArancel() {
        List<estudianteEntity> listaEstudiante = estudianteRepository.findAll();
        boolean lecturaEstudiante = false;
        for (estudianteEntity estudiante : listaEstudiante) {
            if (estudiante != null) {
                crearPlanillaEstudiante(estudiante);
                lecturaEstudiante = true;
            }
        }
        return lecturaEstudiante;
    }

    public double descuentoPorTipoProcedencia(estudianteEntity estudiante, String tipoPago){
        String opcionPago = estudiante.getTipo_establecimiento();
        double descuentoTotal = 0.0;
        double montoTotal = Arancel;
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
        double montoTotal = Arancel;
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
}
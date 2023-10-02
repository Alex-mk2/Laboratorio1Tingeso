package edu.mtisw.monolithicwebapp;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.services.pagoArancelService;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class pagoArancelTest {
    @Autowired
    private edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository pagoArancelRepository;
    @Autowired
    private edu.mtisw.monolithicwebapp.repositories.estudianteRepository estudianteRepository;
    @Autowired
    private edu.mtisw.monolithicwebapp.services.pagoArancelService pagoArancelService;

    @Before

    public void setUp(){
        pagoArancelService = new pagoArancelService();
    }
    @Test
    public void testDescuentoMunicipal() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Municipal");
        double resultado = pagoArancelService.descuentoPorTipoProcedencia(estudiante);
        Assert.assertEquals(0.2 * 1500000, resultado, 0);
    }

    @Test
    public void testDescuentoSubvencionado() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Subvencionado");
        double resultado = pagoArancelService.descuentoPorTipoProcedencia(estudiante);
        assertEquals(0.1 * 1500000, resultado, 0);
    }

    @Test
    public void testDescuentoPrivado() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Privado");
        double resultado = pagoArancelService.descuentoPorTipoProcedencia(estudiante);
        assertEquals(0.0 * 1500000, resultado, 0);
    }

    @Test
    public void testDescuentoPrivadoFallo() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Privado");
        double resultado = pagoArancelService.descuentoPorTipoProcedencia(estudiante);
        assertEquals(0.4 * 1500000, resultado, 0);
        //Se espera que falle, ya que debe ser 0//
    }

    @Test
    public void testDescuentoSubvencionadoFallo() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Subvencionado");
        double resultado = pagoArancelService.descuentoPorTipoProcedencia(estudiante);
        assertEquals(0.5 * 1500000, resultado, 0);
        //Se espera que falle, ya que el estudiante subvencionado tiene 10%//
    }

    @Test
    public void testDescuentoMunicipalFallo() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Municipal");
        double resultado = pagoArancelService.descuentoPorTipoProcedencia(estudiante);
        Assert.assertEquals(0.7 * 1500000, resultado, 0);
        //Se espera que falle, ya que el estudiante municipal tiene 20%//
    }

    @Test
    public void testCantidadCuotasMunicipal() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Municipal");
        int resultado = pagoArancelService.cantidadCuotasEstablecimiento(estudiante);
        assertEquals(10, resultado);
    }

    @Test
    public void testCantidadCuotasSubvencionado() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Subvencionado");
        int resultado = pagoArancelService.cantidadCuotasEstablecimiento(estudiante);
        assertEquals(7, resultado);
    }

    @Test
    public void testCantidadCuotasPrivado() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Privado");
        int resultado = pagoArancelService.cantidadCuotasEstablecimiento(estudiante);
        assertEquals(4, resultado);
    }

    @Test
    public void testCantidadCuotasDesconocido() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Desconocido");
        int resultado = pagoArancelService.cantidadCuotasEstablecimiento(estudiante);
        assertEquals(0, resultado);
    }

    @Test
    public void testDescuentoMenorAUnAnio() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setEgreso("2023");
        int resultado = pagoArancelService.descuentoPorEgreso(estudiante);
        assertEquals((15 * 1500000)/100 , resultado);
    }

    @Test
    public void testDescuentoEntreUnYDosAnios() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setEgreso("2022");
        int resultado = pagoArancelService.descuentoPorEgreso(estudiante);
        assertEquals((8*1500000)/100, resultado);
    }

    @Test
    public void testDescuentoEntreTresYCuatroAnios() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setEgreso("2020");
        int resultado = pagoArancelService.descuentoPorEgreso(estudiante);
        assertEquals((4*1500000)/100, resultado);
    }

    @Test
    public void testDescuentoMasDeCuatroAnios() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setEgreso("2017");
        int resultado = pagoArancelService.descuentoPorEgreso(estudiante);
        assertEquals(0, resultado);
    }

    @Test
    public void testCuotaPagada() {
        pagoArancelEntity pagoArancel = new pagoArancelEntity();
        pagoArancel.setMontoTotalArancel(1000.0);
        pagoArancel.setMontoTotalPagado(1000.0);
        String resultado = pagoArancelService.calcularEstadoCuota(pagoArancel);
        assertEquals("Pagada", resultado);
    }

    @Test
    public void testCuotaEnProcesoDePago() {
        pagoArancelEntity pagoArancel = new pagoArancelEntity();
        pagoArancel.setMontoTotalArancel(2000.0);
        double montoPagadoCuota = 1000;
        pagoArancel.setMontoTotalPagado(montoPagadoCuota);
        String resultado = pagoArancelService.calcularEstadoCuota(pagoArancel);
        assertEquals("Proceso de pago", resultado);
    }

    @Test
    public void testCuotaPendiente() {
        pagoArancelEntity pagoArancel = new pagoArancelEntity();
        pagoArancel.setMontoTotalArancel(1000.00);
        pagoArancel.setMontoTotalPagado(0.0);
        String resultado = pagoArancelService.calcularEstadoCuota(pagoArancel);
        assertEquals("Pendiente", resultado);
    }

    @Test
    public void testCuotaNegativa() {
        pagoArancelEntity pagoArancel = new pagoArancelEntity();
        pagoArancel.setMontoTotalArancel(1000.00);
        pagoArancel.setMontoTotalPagado(-500.00);
        String resultado = pagoArancelService.calcularEstadoCuota(pagoArancel);
        assertEquals("Error: Monto negativo", resultado);
    }
}

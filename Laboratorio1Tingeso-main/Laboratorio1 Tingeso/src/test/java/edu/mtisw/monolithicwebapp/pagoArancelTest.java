package edu.mtisw.monolithicwebapp;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.services.pagoArancelService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository;
import edu.mtisw.monolithicwebapp.repositories.estudianteRepository;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class pagoArancelTest {

    @Mock
    private pagoArancelRepository pagoArancelRepository;

    private estudianteRepository estudianteRepository;

    private pagoArancelService pagoArancelService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pagoArancelService = new pagoArancelService(pagoArancelRepository);
    }

    @Test
    public void testGuardarArancel() {
        pagoArancelEntity pagoArancelEjemplo = new pagoArancelEntity();
        pagoArancelService.guardarArancel(pagoArancelEjemplo);
        verify(pagoArancelRepository).save(pagoArancelEjemplo);
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
    public void testFechaPagoBeforeDay10() {
        LocalDate fechaActualReal = LocalDate.of(2023, 10, 5);
        LocalDate fechaDePago = pagoArancelService.fechaPago();
        assertEquals(fechaActualReal.getYear(), fechaDePago.getYear());
        assertEquals(fechaActualReal.getMonth(), fechaDePago.getMonth());
        assertEquals(fechaActualReal.getDayOfMonth(), fechaDePago.getDayOfMonth());
    }

    @Test
    public void testFechaPagoAfterDay10() {
        LocalDate fechaActualReal = LocalDate.of(2023, 10, 10);
        LocalDate fechaDePago = pagoArancelService.fechaPago();
        assertEquals(fechaActualReal.getYear(), fechaDePago.getYear());
        assertEquals(fechaActualReal.getMonth(), fechaDePago.getMonth());
        assertEquals(fechaActualReal.getDayOfMonth(), fechaDePago.getDayOfMonth());
    }
    @Test
    public void testCrearPlanillaEstudianteContado(){
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setRut("123459");
        estudiante.setNombres("Alex");
        when(estudiante.getNombres()).thenReturn("Alex");
        when(estudiante.getRut()).thenReturn("123459");
        when(estudiante.getIdEstudiante()).thenReturn(1L);
        pagoArancelEntity resultado = pagoArancelService.crearPlanillaEstudiante(estudiante, "Contado");
        assertEquals("Contado", resultado.getTipoPago());
        assertEquals(0.0, resultado.getSaldoPorPagar(), 0);
    }

    @Test
    public void testDescuentoPorPruebaPromedioEntre950Y999() {
        double promedioPuntaje = 975.0;
        double descuentoEsperado = 0.10;
        double descuentoCalculado = pagoArancelService.descuentoPorPrueba(promedioPuntaje);
        assertEquals(descuentoEsperado, descuentoCalculado, 0);
    }

    @Test
    public void testDescuentoPorPruebaPromedioEntre900Y949() {
        double promedioPuntaje = 925.0;
        double descuentoEsperado = 0.05;
        double descuentoCalculado = pagoArancelService.descuentoPorPrueba(promedioPuntaje);
        assertEquals(descuentoEsperado, descuentoCalculado, 0);
    }

    @Test
    public void testDescuentoPorPruebaPromedioEntre850Y899() {
        double promedioPuntaje = 875.0;
        double descuentoEsperado = 0.02;
        double descuentoCalculado = pagoArancelService.descuentoPorPrueba(promedioPuntaje);
        assertEquals(descuentoEsperado, descuentoCalculado, 0);
    }

    @Test
    public void testDescuentoPorPruebaPromedioMenorA850() {
        double promedioPuntaje = 800.0;
        double descuentoEsperado = 0.0;
        double descuentoCalculado = pagoArancelService.descuentoPorPrueba(promedioPuntaje);
        assertEquals(descuentoEsperado, descuentoCalculado, 0);
    }


    @Test
    public void testBuscarPlanillaEstudiante() {
        estudianteEntity estudiante = new estudianteEntity();
        pagoArancelEntity planillaEsperada = new pagoArancelEntity();
        when(pagoArancelRepository.findPlanillaByEstudiante(estudiante)).thenReturn(planillaEsperada);
        pagoArancelEntity resultado = pagoArancelService.buscarPlanillaEstudiante(estudiante);
        assertEquals(planillaEsperada, resultado);
    }

    @Test
    public void testListaArancel() {
        List<pagoArancelEntity> listaArancelesEsperada = new ArrayList<>();
        when(pagoArancelRepository.findAll()).thenReturn(listaArancelesEsperada);
        List<pagoArancelEntity> resultado = pagoArancelService.listaArancel();
        assertEquals(listaArancelesEsperada, resultado);
    }

    @Test
    public void testBuscarEstudiante() {
        String rut = "123456789";
        pagoArancelEntity estudiante = new pagoArancelEntity();
        when(pagoArancelRepository.findEstudianteByRut(rut)).thenReturn(estudiante);
        pagoArancelEntity resultado = pagoArancelService.buscarEstudiante(rut);
        assertEquals(estudiante, resultado);
    }

    @Test
    public void testCalcularArancelConEstudiantes() {
        List<estudianteEntity> listaEstudiantes = new ArrayList<>();
        when(estudianteRepository.findAll()).thenReturn(listaEstudiantes);
        when(pagoArancelService.crearPlanillaEstudiante(any(estudianteEntity.class), eq("Cuotas"))).thenReturn(new pagoArancelEntity());
        boolean resultado = pagoArancelService.calcularArancel();
        assertTrue(resultado);
        for (estudianteEntity estudiante : listaEstudiantes) {
            verify(pagoArancelService).crearPlanillaEstudiante(estudiante, "Cuotas");
            verify(pagoArancelService).guardarArancel(any(pagoArancelEntity.class));
        }
    }

    @Test
    public void testAtrasosSinDescuento() {
        LocalDate fechaActual = LocalDate.of(2023, 10, 11); // Día después del 10 de octubre
        double descuento = pagoArancelService.atrasos(fechaActual);
        assertEquals(0.0, descuento, 0.001); // No debería haber descuento
    }
}

package edu.mtisw.monolithicwebapp;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import edu.mtisw.monolithicwebapp.services.pagoArancelService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.mockito.Mockito;


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
    public void testCrearPlanillaEstudianteContado() {
        estudianteEntity estudiante = new estudianteEntity();
        String tipoPago = "Contado";
        Mockito.when(pagoArancelService.descuentoPorEgreso(estudiante)).thenReturn(100);
        pagoArancelEntity pagoArancel = pagoArancelService.crearPlanillaEstudiante(estudiante, tipoPago);
        assertEquals("Contado", pagoArancel.getTipoPago());
        assertEquals(750000.0, pagoArancel.getMontoTotalArancel(), 0); // Tolerancia de error de 0.01 debido a números flotantes
    }

    @Test
    public void testDescuentoPorPruebaPromedioEntre950Y999() {
        double promedioPuntaje = 975.0;
        double descuentoEsperado = 0.10;
        double descuentoCalculado = pagoArancelService.descuentoPorPrueba(promedioPuntaje);
        assertEquals(descuentoEsperado, descuentoCalculado, 0); // Tolerancia de error de 0.01 debido a números flotantes
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
}

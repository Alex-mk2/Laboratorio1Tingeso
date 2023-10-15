package edu.mtisw.monolithicwebapp;
import edu.mtisw.monolithicwebapp.entities.PruebaEntity;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import edu.mtisw.monolithicwebapp.repositories.pruebaRepository;
import edu.mtisw.monolithicwebapp.services.PruebaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import java.time.LocalDate;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;

@ExtendWith(MockitoExtension.class)
public class PruebaTest{
    @Mock
    private pruebaRepository pruebaRepository;
    @InjectMocks
    private PruebaService pruebaService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        pruebaService = new PruebaService(pruebaRepository);
    }

    @Test
    public void testFindPruebaByEstudiante_IdEstudiante(){
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setIdEstudiante(1L);
        estudiante.setNombres("Nombre");
        estudiante.setApellidos("Apellido");
        estudiante.setEmail("email@example.com");
        estudiante.setRut("12345");
        estudiante.setFecha_nacimiento(String.valueOf(LocalDate.parse("1990-01-01")));
        estudiante.setTipo_establecimiento("Tipo");
        estudiante.setNombre_establecimiento("Nombre Establecimiento");
        estudiante.setEgreso("Egreso");
        List<pagoArancelEntity> pagosArancel = new ArrayList<>();
        estudiante.setPagoArancel(pagosArancel);
        List<PruebaEntity> pruebas = new ArrayList<>();
        estudiante.setPruebas(pruebas);
        Long idEstudiante = 1L;
        estudianteEntity estudiante1 = new estudianteEntity(1L, "Nombre", "Apellido", "email@example.com", "12345", "1990-01-01", "Tipo", "Nombre Establecimiento", "Egreso", pagosArancel, pruebas);
        pruebas.add(new PruebaEntity(1L, estudiante1, LocalDate.now(), 90, LocalDate.now()));
        pruebas.add(new PruebaEntity(2L, estudiante, LocalDate.now(), 85, LocalDate.now()));
        when(pruebaRepository.findPruebaByEstudiante_IdEstudiante(idEstudiante)).thenReturn((ArrayList<PruebaEntity>) pruebas);
        List<PruebaEntity> resultado = pruebaService.findPruebaByEstudiante_IdEstudiante(idEstudiante);
        assertEquals(pruebas, resultado);
    }

    @Test
    public void testGuardarPruebaEstudiante() {
        PruebaEntity prueba = new PruebaEntity();
        prueba.setFecha_examen(LocalDate.now());
        prueba.setPuntaje_obtenido(95);
        pruebaService.guardarPruebaEstudiante(prueba);
        verify(pruebaRepository, times(1)).save(prueba);
    }

    @Test
    public void testObtenerDatosPruebas() {
        List<PruebaEntity> pruebas = new ArrayList<>();
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setIdEstudiante(1L);
        estudiante.setNombres("Nombre Estudiante");
        estudiante.setApellidos("Apellido Estudiante");
        estudiante.setEmail("email@example.com");
        pruebas.add(new PruebaEntity(1L, estudiante, LocalDate.now(), 90, LocalDate.now()));
        pruebas.add(new PruebaEntity(2L, estudiante, LocalDate.now(), 85, LocalDate.now()));
        when(pruebaRepository.findAll()).thenReturn(pruebas);
        List<PruebaEntity> resultado = pruebaService.ObtenerDatosPruebas();
        assertEquals(pruebas, resultado);
    }
}


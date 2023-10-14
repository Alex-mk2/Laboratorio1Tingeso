package edu.mtisw.monolithicwebapp;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import edu.mtisw.monolithicwebapp.services.estudianteService;
import edu.mtisw.monolithicwebapp.repositories.estudianteRepository;
import org.mockito.MockitoAnnotations;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

public class EstudianteTest {

    @Mock
    private estudianteRepository estudianteRepository;


    private estudianteService estudianteService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        estudianteService = new estudianteService(estudianteRepository);
    }

    @Test
    public void testObtenerEstudiante() {
        List<estudianteEntity> estudiantes = new ArrayList<>();
        estudianteEntity estudiante1 = new estudianteEntity();
        estudiante1.setIdEstudiante(1L);
        estudiante1.setNombres("Nombre1");
        estudianteEntity estudiante2 = new estudianteEntity();
        estudiante2.setIdEstudiante(2L);
        estudiante2.setNombres("Nombre2");
        estudiantes.add(estudiante1);
        estudiantes.add(estudiante2);
        Mockito.when(estudianteRepository.findAll()).thenReturn(estudiantes);
        List<estudianteEntity> resultado = estudianteService.obtenerEstudiante();
        assertEquals(estudiantes, resultado);
    }

    @Test
    public void testGuardarEstudiante() {
        estudianteEntity nuevoEstudiante = new estudianteEntity();
        nuevoEstudiante.setIdEstudiante(1L);
        nuevoEstudiante.setNombres("NuevoEstudiante");
        when(estudianteRepository.save(nuevoEstudiante)).thenReturn(nuevoEstudiante);
        estudianteEntity resultado = estudianteService.guardarEstudiante(nuevoEstudiante);
        assertEquals(nuevoEstudiante, resultado);
    }

    @Test
    public void testFindEstudiantePorId() {
        estudianteEntity estudianteEjemplo = new estudianteEntity();
        estudianteEjemplo.setIdEstudiante(1L);
        estudianteEjemplo.setNombres("Estudiante Ejemplo");
        when(estudianteRepository.findByIdEstudiante(1L)).thenReturn(estudianteEjemplo);
        estudianteEntity resultado = estudianteService.findByIdEstudiante(1L);
        assertEquals(estudianteEjemplo, resultado);
    }
}

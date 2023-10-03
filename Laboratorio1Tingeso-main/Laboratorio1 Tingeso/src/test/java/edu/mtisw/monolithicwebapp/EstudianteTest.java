package edu.mtisw.monolithicwebapp;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.services.estudianteService;
import edu.mtisw.monolithicwebapp.repositories.estudianteRepository;

@DataJpaTest
public class EstudianteTest {

    @Autowired
    private estudianteService estudianteService;
    @Autowired
    private estudianteRepository estudianteRepository;

    @Before

    public void setUp(){
        estudianteService = new estudianteService();
    }

    @Test
    public void testFindByIdEstudiante() {
        Long idEstudiante = 1L;
        estudianteEntity estudianteDePrueba = new estudianteEntity();
        estudianteDePrueba.setIdEstudiante(idEstudiante);
        estudianteDePrueba.setNombres("Nombre de prueba");
        estudianteEntity resultado = estudianteService.findByIdEstudiante(idEstudiante);
        assertEquals(idEstudiante, resultado.getIdEstudiante());
        assertEquals("Nombre de prueba", resultado.getNombres());
    }
}

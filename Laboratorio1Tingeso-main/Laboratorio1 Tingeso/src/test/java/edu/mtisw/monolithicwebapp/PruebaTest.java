package edu.mtisw.monolithicwebapp;
import edu.mtisw.monolithicwebapp.entities.PruebaEntity;
import edu.mtisw.monolithicwebapp.services.estudianteService;
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
import org.springframework.mock.web.MockMultipartFile;
import edu.mtisw.monolithicwebapp.repositories.estudianteRepository;

@ExtendWith(MockitoExtension.class)
public class PruebaTest{
    @Mock
    private pruebaRepository pruebaRepository;
    @InjectMocks
    private PruebaService pruebaService;
    @Mock
    private estudianteRepository estudianteRepository;
    @InjectMocks
    private estudianteService estudianteService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        estudianteService = new estudianteService(estudianteRepository);

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

    @Test
    public void testVerificarArchivoArchivoVacio() {
        MockMultipartFile emptyFile = new MockMultipartFile("archivo.csv", new byte[0]);
        String resultado = pruebaService.VerificarArchivo(emptyFile);
        assertEquals("Archivo vacío", resultado);
    }

    @Test
    public void testVerificarArchivoFormatoIncorrecto() {
        String contenido = "Rut,Puntaje\n123456789,95";
        MockMultipartFile invalidFile = new MockMultipartFile("archivo.csv", "archivo.csv", "text/csv", contenido.getBytes());
        String resultado = pruebaService.VerificarArchivo(invalidFile);
        assertEquals("El archivo debe poseer 3 columnas: Rut, puntaje, fecha", resultado);
    }

    @Test
    public void testVerificarArchivoPuntajeNoNumero() {
        String contenido = "Rut,Puntaje,fecha\n123456789,NoEsNumero,01-01-2022";
        MockMultipartFile invalidPuntajeFile = new MockMultipartFile("archivo.csv", "archivo.csv", "text/csv", contenido.getBytes());
        String resultado = pruebaService.VerificarArchivo(invalidPuntajeFile);
        assertEquals("Puntaje debe ser un número", resultado);
    }

    @Test
    public void testEliminarPruebas() {
        PruebaEntity prueba1 = new PruebaEntity();
        prueba1.setIdPrueba(1L);
        PruebaEntity prueba2 = new PruebaEntity();
        prueba2.setIdPrueba(2L);
        List<PruebaEntity> pruebas = new ArrayList<>();
        pruebas.add(prueba1);
        pruebas.add(prueba2);
        pruebaService.EliminarPruebas((ArrayList<PruebaEntity>) pruebas);
        verify(pruebaRepository).deleteAll(pruebas);
    }

    @Test
    public void testPromediosPruebasEstudianteConPruebasVacias() {
        List<PruebaEntity> pruebasVacias = new ArrayList<>();
        Integer resultado = pruebaService.PromediosPruebasEstudiante((ArrayList<PruebaEntity>) pruebasVacias);
        assertEquals(0, resultado);
    }

    @Test
    public void testPromediosPruebasEstudianteConPruebasNoVacias() {
        List<PruebaEntity> pruebasNoVacias = new ArrayList<>();
        pruebasNoVacias.add(new PruebaEntity(1L, null, null, 90, null));
        pruebasNoVacias.add(new PruebaEntity(2L, null, null, 85, null));
        pruebasNoVacias.add(new PruebaEntity(3L, null, null, 75, null));
        Integer resultado = pruebaService.PromediosPruebasEstudiante((ArrayList<PruebaEntity>) pruebasNoVacias);
        assertEquals(83, resultado);
    }
}


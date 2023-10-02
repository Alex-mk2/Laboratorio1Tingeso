package edu.mtisw.monolithicwebapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import static org.junit.Assert.assertEquals;
@SpringBootTest
public class EstudianteTest{
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.estudianteRepository estudianteRepository;
    @Autowired
    edu.mtisw.monolithicwebapp.repositories.pagoArancelRepository pagoArancelRepository;





    @Test
    public void testDescuentoSubvencionado() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Subvencionado");
        //double resultado = descuentoPorTipoProcedencia(estudiante);
        //assertEquals(0.1 * 1500000, resultado, 0.001);
    }

    @Test
    public void testDescuentoPrivado() {
        estudianteEntity estudiante = new estudianteEntity();
        estudiante.setTipo_establecimiento("Privado");
        //double resultado = descuentoPorTipoProcedencia(estudiante);
        //assertEquals(0.0 * Arancel, resultado, 0.001);
    }

}

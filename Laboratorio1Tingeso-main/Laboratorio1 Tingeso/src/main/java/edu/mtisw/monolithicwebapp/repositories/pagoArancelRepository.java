package edu.mtisw.monolithicwebapp.repositories;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface pagoArancelRepository extends JpaRepository<pagoArancelEntity, Long>{

    pagoArancelEntity findEstudianteByRut(String rut);

    pagoArancelEntity findPlanillaByEstudiante(estudianteEntity estudiante);


    List<pagoArancelEntity> findEstudiantByRut(String rut);
}

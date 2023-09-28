package edu.mtisw.monolithicwebapp.repositories;
import edu.mtisw.monolithicwebapp.entities.pagoArancelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface pagoArancelRepository extends JpaRepository<pagoArancelEntity, Long>{

    pagoArancelEntity findEstudianteByRut(String rut);
}

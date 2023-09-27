package edu.mtisw.monolithicwebapp.repositories;
import edu.mtisw.monolithicwebapp.entities.estudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface estudianteRepository extends JpaRepository<estudianteEntity, Long> {
    estudianteEntity findByEmail(String email);

    estudianteEntity findEstudianteByRut(String rut);
}
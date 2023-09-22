package edu.mtisw.monolithicwebapp.repositories;
import edu.mtisw.monolithicwebapp.entities.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
    public EstudianteEntity findByEmail(String email);

}
package edu.mtisw.monolithicwebapp.repositories;
import edu.mtisw.monolithicwebapp.entities.CuotasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuotasRepository extends JpaRepository<CuotasEntity, Long>{

}

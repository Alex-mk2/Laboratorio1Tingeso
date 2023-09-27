package edu.mtisw.monolithicwebapp.repositories;


import edu.mtisw.monolithicwebapp.entities.PruebaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface pruebaRepository extends JpaRepository<PruebaEntity, Long>{

}

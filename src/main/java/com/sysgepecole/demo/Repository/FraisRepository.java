package com.sysgepecole.demo.Repository;




import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sysgepecole.demo.Models.Frais;

public interface FraisRepository extends  JpaRepository<Frais, Long>{

	
	Optional<Frais> findById(Long idfrais);
	
	
    List<Frais> findByIntermedaireClasse_IdintermedaireclasseAndIntermedaireAnnee_Idintermedaireannee(Long idintermedaireclasse, Long idintermedaireannee);

	
	
	
	
}

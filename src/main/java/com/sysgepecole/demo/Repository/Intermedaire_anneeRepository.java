package com.sysgepecole.demo.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sysgepecole.demo.Models.Intermedaire_annee;

public interface Intermedaire_anneeRepository extends  JpaRepository<Intermedaire_annee, Long>{

	
	Optional<Intermedaire_annee> findById(Long idintermedaireannee);

	Optional<Intermedaire_annee> findByAnnee_IdanneeAndEcole_Idecole(Long idannee, long idecole);
   


}

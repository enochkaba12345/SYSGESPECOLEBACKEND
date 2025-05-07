package com.sysgepecole.demo.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sysgepecole.demo.Models.Intermedaire_classe;

public interface Intermedaire_classeRepository extends  JpaRepository<Intermedaire_classe, Long>{
	
	Optional<Intermedaire_classe> findById(Long idintermedaireclasse);
	   Optional<Intermedaire_classe> findByClasse_IdclasseAndEcole_Idecole(Long idclasse, Long idecole);
	

}

package com.sysgepecole.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sysgepecole.demo.Models.Annee;


public interface AnneeRepository extends  JpaRepository<Annee, Long>{

	
	Optional<Annee> findByAnnee(String annee);

}

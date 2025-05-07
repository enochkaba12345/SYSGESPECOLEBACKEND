package com.sysgepecole.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sysgepecole.demo.Models.Eleve;


public interface EleveRepository extends  JpaRepository<Eleve, Long>{

	Optional<Eleve> findByNomAndPostnomAndPrenomAndIdintermedaireclasseAndIdintermedaireannee(String nom, String postnom, String prenom,Long idintermedaireclasse,Long idintermedaireannee);

	@Query("SELECT e.telephone FROM Eleve e WHERE e.ideleve = :ideleve")
    String findTelephoneByIdeleve(@Param("ideleve") Long ideleve);
}

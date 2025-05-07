package com.sysgepecole.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysgepecole.demo.Models.Classe;


public interface ClasseRepository extends  JpaRepository<Classe, Long>{

	
	Optional<Classe> findByClasse(String classe);
}

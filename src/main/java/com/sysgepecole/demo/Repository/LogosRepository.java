package com.sysgepecole.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysgepecole.demo.Models.Logos;


public interface LogosRepository extends JpaRepository<Logos, Long>{
	
	Optional<Logos> findByIdecole(Long idecole);

}

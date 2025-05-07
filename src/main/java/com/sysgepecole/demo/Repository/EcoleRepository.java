package com.sysgepecole.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysgepecole.demo.Models.Ecole;

public interface EcoleRepository extends  JpaRepository<Ecole, Long>{

	Optional<Ecole> findByEcole(String ecole);

}

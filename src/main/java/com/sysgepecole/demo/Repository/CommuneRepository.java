package com.sysgepecole.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysgepecole.demo.Models.Commune;

public interface CommuneRepository extends  JpaRepository<Commune, Long>{

}

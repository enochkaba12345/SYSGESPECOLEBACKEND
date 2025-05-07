package com.sysgepecole.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sysgepecole.demo.Models.Niveau;


@Repository
public interface NiveauRepository extends  JpaRepository<Niveau, Long>{

}

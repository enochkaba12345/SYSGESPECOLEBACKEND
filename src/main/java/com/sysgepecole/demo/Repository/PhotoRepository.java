package com.sysgepecole.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysgepecole.demo.Models.Photo;


public interface PhotoRepository extends JpaRepository<Photo, Long>{

	Optional<Photo> findByIdeleve(Long ideleve);

}

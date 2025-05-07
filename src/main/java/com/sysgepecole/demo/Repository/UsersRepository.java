package com.sysgepecole.demo.Repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sysgepecole.demo.Models.Users;

public interface UsersRepository extends  JpaRepository<Users, Long>{
	
	Optional<Users> findByUsername(String username);
	
	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
	
	

}

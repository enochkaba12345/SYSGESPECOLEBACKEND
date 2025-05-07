package com.sysgepecole.demo.Service;

import java.util.List;


import org.springframework.http.ResponseEntity;

import com.sysgepecole.demo.Models.Commune;


public interface CommuneService {

	
	 List<Commune> getAllCommune();
	 ResponseEntity<?> getcommuneByIdprovince(Long idprovince);
}

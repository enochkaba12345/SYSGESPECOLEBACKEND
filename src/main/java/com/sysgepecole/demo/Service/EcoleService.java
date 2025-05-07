package com.sysgepecole.demo.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;


import com.sysgepecole.demo.Models.Ecole;

public interface EcoleService {

	
	 
	 ResponseEntity<Map<String, Boolean>> delete(Long idecole);
	 List<Ecole> getAllEcole();
	 Optional<Ecole> findEcoleByEcole(String ecole);
	 Ecole createEcoles(Ecole ecole);
	 ResponseEntity<Ecole> updateEcoles(Long idecole, Ecole ecole);
	 ResponseEntity<?> CollectionEcole();
	 ResponseEntity<?> getEcole(long idecole);
	 ResponseEntity<?> CollectionEcoles(long idecole);


}

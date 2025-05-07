package com.sysgepecole.demo.Service;


import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.sysgepecole.demo.Models.Annee;


public interface AnneeService {

	
	 Annee CreateAnnee (Annee annee);
	 ResponseEntity<Annee> updateAnnee(Long idannee, Annee annee);
	 ResponseEntity<Map<String, Boolean>> delete(Long idannee);
	 ResponseEntity<?> getAllAnnee();
	 ResponseEntity<?> CollecteAnnee(long idintermedaireclasse);
	 ResponseEntity<?> CollecteEcoleAnnee(long idecole);
	 ResponseEntity<?> CollecteCategorieAnnee(long idtranche,long idcategorie,long idecole);
	 ResponseEntity<?> CollecteCategorieAnneeAdmin(long idtranche,long idcategorie);
	 Optional<Annee> findAnneeByAnnee(String annee);
}

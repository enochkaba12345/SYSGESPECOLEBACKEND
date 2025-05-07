package com.sysgepecole.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.sysgepecole.demo.Dto.FraisDto;
import com.sysgepecole.demo.Models.Frais;


public interface FraisService {

	

	 ResponseEntity<Map<String, Boolean>> delete(Long idfrais);
	 List<Frais> getAllFrais();
	 Frais saveFrais(FraisDto fraisDto);
	 ResponseEntity<?> CollecteTranche();
	 ResponseEntity<?> CollecteEcoleTranche(long idecole);
	 ResponseEntity<?> CollecteEcoleTrancheClasse(long idecole,long idtranche);
	 ResponseEntity<?> CollecteClasse(Long itranche);
	 ResponseEntity<?> CollecteFrais(Long idclasse, Long idtranche);
	 ResponseEntity<?> CollecteFraisClasse(long idecole,long idclasse, long idtranche);

	Frais updateFrais(Long idfrais, FraisDto fraisDto);
	
}

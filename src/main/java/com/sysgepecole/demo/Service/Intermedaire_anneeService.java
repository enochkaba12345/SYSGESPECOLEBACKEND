package com.sysgepecole.demo.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;


import com.sysgepecole.demo.Dto.IntermedaireAnneeDto;
import com.sysgepecole.demo.Models.Intermedaire_annee;

public interface Intermedaire_anneeService {


	 Intermedaire_annee updateIntermedaireAnnee(IntermedaireAnneeDto intermedaireAnneeDto);
	 Optional<Intermedaire_annee> getIntermedaireAnneeById(Long idintermedaireannee);
	 ResponseEntity<Map<String, Boolean>> delete(Long idintermedaireannee);
	 List<Intermedaire_annee> getAllIntermedaireannee();
	 Intermedaire_annee createIntermedaireAnnee(IntermedaireAnneeDto requestDto);
	List<IntermedaireAnneeDto> getInterannees();
	List<IntermedaireAnneeDto> getrannees(long idecole);
	List<IntermedaireAnneeDto> getIdInterannees(long idintermedaireannee);
}


  


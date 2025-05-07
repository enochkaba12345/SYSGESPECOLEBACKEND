package com.sysgepecole.demo.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;


import com.sysgepecole.demo.Dto.IntermedaireClasseDto;
import com.sysgepecole.demo.Models.Intermedaire_classe;

public interface Intermedaire_classeService {
	
	
	 ResponseEntity<Map<String, Boolean>> delete(Long idintermedaireclasse);
	 List<Intermedaire_classe> getAllIntermedaireclasse();
	 Intermedaire_classe createIntermedaireClasse(IntermedaireClasseDto requestDto);
	Intermedaire_classe updateIntermedaireClasse(IntermedaireClasseDto requestDto);
	Optional<Intermedaire_classe> getIntermedaireClasseById(Long idintermedaireclasse);
	List<IntermedaireClasseDto> getInterecoles();
	List<IntermedaireClasseDto> getInterniveaux(long idecole);
	List<IntermedaireClasseDto> getniveauclasses(long idecole,long idniveau);
	List<IntermedaireClasseDto> getIdInterclasses(long idintermedaireclasse);
	List<IntermedaireClasseDto> getInterecoleNoAdmins(Long idecole);

}

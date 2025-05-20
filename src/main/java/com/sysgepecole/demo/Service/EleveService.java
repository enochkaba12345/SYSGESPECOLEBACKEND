package com.sysgepecole.demo.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sysgepecole.demo.Dto.EleveModelDto;
import com.sysgepecole.demo.Dto.EleveUpdateDTO;
import com.sysgepecole.demo.Dto.UserModelDto;
import com.sysgepecole.demo.Models.Eleve;

import net.sf.jasperreports.engine.JRException;


public interface EleveService {

	
	 ResponseEntity<Eleve> updateEleve(Long ideleve, Eleve eleve);
	 Optional<Eleve> findEleveByNomPostnomPrenom(String nom, String postnom, String prenom,Long idintermedaireclasse,Long idintermedaireannee);
	 Eleve createEleve(Eleve eleve);
	 ResponseEntity<?> CollecteEleve();
	 ResponseEntity<?> CollecteEleveses(long idecole);
	 ResponseEntity<?> CollecteElevedashbord(long idecole,long idclasse,long idannee);
	 ResponseEntity<?> FicheEleve(Long ideleve) throws FileNotFoundException, JRException;
	 ResponseEntity<?> FicheClasse(long idecole,long idclasse,long idannee) throws FileNotFoundException, JRException;
	 List<EleveModelDto> searchEleves(String nom, Long idecole, boolean isAdmin);
	 ResponseEntity<?> CollecteAnneeEleve(long idintermedaireclasse,long idintermedaireannee) ;
	 ResponseEntity<?> CollecteClasseAnneeEleve(long idintermedaireclasse,long idintermedaireannee);
	 ResponseEntity<?> EleveParClasse(long idecole,long idclasse,long idannee) ;
	 ResponseEntity<?> ElevePar(long idecole,long idclasse,long idannee,long ideleve) ;
	 void enregistrerIscription(String username, String noms, String classe, String ecole, String annee);

	 
	


}

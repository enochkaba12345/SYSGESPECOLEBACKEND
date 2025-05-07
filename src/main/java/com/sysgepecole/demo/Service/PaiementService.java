package com.sysgepecole.demo.Service;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sysgepecole.demo.Dto.EleveModelDto;
import com.sysgepecole.demo.Dto.PaiementDto;
import com.sysgepecole.demo.Models.Paiement;

import net.sf.jasperreports.engine.JRException;

public interface PaiementService {


	 Paiement createPaiement(Paiement paiement);
	 ResponseEntity<?> findByIdeleve(long ideleve, long idintermedaireclasse);
	 ResponseEntity<?> CollecteFraisPayerEleve(long ideleve,long idtranche,long idcategorie);
	 ResponseEntity<?> CollectionPaiement(Long ideleve);
	 ResponseEntity<?> getPaiementsByEleve(Long ideleve);
	 ResponseEntity<?> CollectionPaiementses(long idecole);
	 ResponseEntity<?> CollectionPaiementdashbord(long idecole,long idclasse,long idannee);
	 ResponseEntity<?> CollectionPaiementMode(Long idpaiement);
	 ResponseEntity<?> ImpressionRecuEleveAcompte(long ideleve) throws FileNotFoundException, JRException;
	 ResponseEntity<?> ImpressionRecuEleveSolde(long ideleve) throws FileNotFoundException, JRException;
	 ResponseEntity<?> ImpressionRecuModeEleveAcompte(long idpaiement) throws FileNotFoundException, JRException;
	 ResponseEntity<?> ImpressionRecuModeEleveSolde(long idpaiement) throws FileNotFoundException, JRException;
	 ResponseEntity<?> ImpressionRecuModeEleve(long idpaiement) throws FileNotFoundException, JRException;
	 ResponseEntity<?> EcoleParClasse(Long idecole,Long idclasse,Long idannee) ;
	 ResponseEntity<?> FichePaiementeleve(Long ideleve ) throws FileNotFoundException, JRException;
	 List<PaiementDto> searchPaiements(String nom, Long idecole, boolean isAdmin);
	 ResponseEntity<?> PaiementDeleve(Long ideleve);
	 ResponseEntity<Paiement> updatePaiement(Long idpaiement, Paiement paiement);
	 ResponseEntity<?> CollectionPaiementAcompte(Long ideleve);
	 ResponseEntity<?> CollectionPaiementSolde(Long ideleve);
	 ResponseEntity<?> FicheRecouvrementClasse(long idecole, long idclasse, long idannee) throws FileNotFoundException, JRException;
	 void enregistrerPaiement(String username, String nom, String classe, String ecole, String annee, double montant, String frais);


}

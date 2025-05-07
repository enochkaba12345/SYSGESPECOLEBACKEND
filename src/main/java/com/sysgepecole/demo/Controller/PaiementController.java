package com.sysgepecole.demo.Controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Dto.EleveModelDto;
import com.sysgepecole.demo.Dto.PaiementDto;
import com.sysgepecole.demo.Models.Paiement;
import com.sysgepecole.demo.Service.PaiementService;

import net.sf.jasperreports.engine.JRException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/paiement")
public class PaiementController {
	
	@Autowired
	PaiementService paiementservice;

	@GetMapping("/findPaiement/{ideleve}/{idintermedaireclasse}") 
	public ResponseEntity<?> findPaiement(@PathVariable long ideleve, @PathVariable long idintermedaireclasse) {
		return paiementservice.findByIdeleve(ideleve, idintermedaireclasse); 
		}
	
	@GetMapping("/CollecteFraisPayerEleve/{ideleve}/{idtranche}/{idcategorie}") 
	public ResponseEntity<?> CollecteFraisPayerEleve(@PathVariable long ideleve,@PathVariable long idtranche,@PathVariable long idcategorie) {
		return paiementservice.CollecteFraisPayerEleve(ideleve,idtranche,idcategorie); 
		}
	
	  @PostMapping("/createPaiement")
	    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) {
	        Paiement savedPaiement = paiementservice.createPaiement(paiement);
	        return new ResponseEntity<>(savedPaiement, HttpStatus.CREATED);
	    }

	 @GetMapping("/CollectionPaiement/{ideleve}")
	    public ResponseEntity<?> CollectionPaiement(@PathVariable Long ideleve) {
	        return paiementservice.CollectionPaiement(ideleve);
	    }
	 
	 @GetMapping("/getPaiementsByEleve/{ideleve}")
	    public ResponseEntity<?> getPaiementsByEleve(@PathVariable Long ideleve) {
	        return paiementservice.getPaiementsByEleve(ideleve);
	    }
	 
	 @GetMapping("/CollectionPaiementAcompte/{ideleve}")
	    public ResponseEntity<?> CollectionPaiementAcompte(@PathVariable Long ideleve) {
	        return paiementservice.CollectionPaiementAcompte(ideleve);
	    }
	 
	 @GetMapping("/CollectionPaiementSolde/{ideleve}")
	    public ResponseEntity<?> CollectionPaiementSolde(@PathVariable Long ideleve) {
	        return paiementservice.CollectionPaiementSolde(ideleve);
	    }
	 
	 @GetMapping("/CollectionPaiementdashbord/{idecole}/{idclasse}/{idannee}")
	    public ResponseEntity<?> CollectionPaiementdashbord(@PathVariable Long idecole,@PathVariable Long idclasse,@PathVariable Long idannee)  {
	        return paiementservice.CollectionPaiementdashbord(idecole,idclasse,idannee);
	    }
	 
	 @GetMapping("/CollectionPaiementes/{idecole}")
	    public ResponseEntity<?> CollectionPaiementes(@PathVariable Long idecole) {
	        return paiementservice.CollectionPaiementses(idecole);
	    }
	 
		@GetMapping("/ImpressionRecuEleveAcompte/{ideleve}")
		public ResponseEntity<?> ImpressionRecuEleveAcompte(@PathVariable Long ideleve) throws FileNotFoundException, JRException {
			return paiementservice.ImpressionRecuEleveAcompte(ideleve);
		}
		
		@GetMapping("/ImpressionRecuEleveSolde/{ideleve}")
		public ResponseEntity<?> ImpressionRecuEleveSolde(@PathVariable Long ideleve) throws FileNotFoundException, JRException {
			return paiementservice.ImpressionRecuEleveSolde(ideleve);
		}
		
		@GetMapping("/ImpressionRecuModeEleve/{idpaiement}")
		public ResponseEntity<?> ImpressionRecuModeEleve(@PathVariable Long idpaiement) throws FileNotFoundException, JRException {
			return paiementservice.ImpressionRecuModeEleve(idpaiement);
		}
		
		@GetMapping("/ImpressionRecuModeEleveSolde/{idpaiement}")
		public ResponseEntity<?> ImpressionRecuModeEleveSolde(@PathVariable Long idpaiement) throws FileNotFoundException, JRException {
			return paiementservice.ImpressionRecuModeEleveSolde(idpaiement);
		}
		
		@GetMapping("/ImpressionRecuModeEleveAcompte/{idpaiement}")
		public ResponseEntity<?> ImpressionRecuModeEleveAcompte(@PathVariable Long idpaiement) throws FileNotFoundException, JRException {
			return paiementservice.ImpressionRecuModeEleveAcompte(idpaiement);
		}
		
		 
		 @GetMapping("/searchPaiement")
		    public ResponseEntity<?> searchPaiement(@RequestParam String userRole,
		    									  @RequestParam String nom, 
		                                          @RequestParam(required = false) Long idecole) {
		        boolean isAdmin = "ADMIN".equalsIgnoreCase(userRole);
		        
		        List<PaiementDto> collections = paiementservice.searchPaiements(nom, idecole, isAdmin);

		        if (collections.isEmpty()) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé.");
		        }
		        return ResponseEntity.ok(collections);
		    }
		
			@GetMapping("/PaiementDeleve/{ideleve}")
			public ResponseEntity<?> PaiementDeleve(@PathVariable long ideleve){
				return paiementservice.PaiementDeleve(ideleve);
			}
			
			@GetMapping("/CollectionPaiementMode/{idpaiement}")
			public ResponseEntity<?> CollectionPaiementMode(@PathVariable long idpaiement){
				return paiementservice.CollectionPaiementMode(idpaiement);
			}
			
			@PutMapping("/updatePaiement/{ideleve}")
			public ResponseEntity<Paiement> updatePaiement(@PathVariable Long ideleve, @RequestBody Paiement paiementDetails) {
				return paiementservice.updatePaiement(ideleve, paiementDetails);
			}
			
			@GetMapping("/EleveParClasse/{idecole}/{idclasse}/{idannee}")
			public ResponseEntity<?> EleveParClasse(@PathVariable Long idecole,@PathVariable Long idclasse,@PathVariable Long idannee)  {
				return paiementservice.EcoleParClasse(idecole,idclasse,idannee);
			}
			
			 @GetMapping("/FichePaiementeleve/{ideleve}")
			    public ResponseEntity<?> FichePaiementeleve(@PathVariable Long ideleve) throws FileNotFoundException, JRException {
			        return paiementservice.FichePaiementeleve(ideleve);
			    }
			 
			 @GetMapping("/FicheRecouvrementClasse/{idecole}/{idclasse}/{idannee}")
			    public ResponseEntity<?> FicheRecouvrementClasse(@PathVariable Long idecole,@PathVariable Long idclasse,@PathVariable Long idannee) throws FileNotFoundException, JRException {
			        return paiementservice.FicheRecouvrementClasse(idecole,idclasse,idannee);
			    }
			 
			 @PostMapping("/effectuerPaiement")
			    public ResponseEntity<String> effectuerPaiement(@RequestParam String username,
			                                                    @RequestParam String nom,
			                                                    @RequestParam String classe,
			                                                    @RequestParam String ecole,
			                                                    @RequestParam String annee,
			                                                    @RequestParam double montant,
			                                                    @RequestParam String frais) {
				 paiementservice.enregistrerPaiement(username, nom, classe, ecole, annee, montant,frais);
			        return ResponseEntity.ok("✅ Paiement enregistré !");
			    }
}

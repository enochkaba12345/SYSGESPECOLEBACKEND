package com.sysgepecole.demo.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.sysgepecole.demo.Models.Eleve;
import com.sysgepecole.demo.Service.EleveService;

import net.sf.jasperreports.engine.JRException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/eleve")
public class EleveController {

	@Autowired
	private EleveService eleveService;

	@PostMapping
	public ResponseEntity<Eleve> createEleve(@RequestBody Eleve eleve) {
		try {
			Eleve createdEleve = eleveService.createEleve(eleve);
			return ResponseEntity.ok(createdEleve);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PutMapping("/updateEleve/{ideleve}")
	public ResponseEntity<Eleve> updateEleve(@PathVariable Long ideleve, @RequestBody Eleve eleve) {
		return eleveService.updateEleve(ideleve, eleve);
	}



	@GetMapping("/findEleve/{nom}/{postnom}/{prenom}/{idintermedaireclasse}/{idintermedaireannee}")
	public Optional<Eleve> findEleve(@PathVariable String nom, @PathVariable String postnom,
			@PathVariable String prenom, @PathVariable Long idintermedaireclasse,
			@PathVariable Long idintermedaireannee) {
		return eleveService.findEleveByNomPostnomPrenom(nom, postnom, prenom, idintermedaireclasse,
				idintermedaireannee);
	}

	@GetMapping("/CollectionEleve")
	public ResponseEntity<?> collectionEleve() {
		return eleveService.CollecteEleve();
	}

	@GetMapping("/CollectionEleves/{idecole}")
	public ResponseEntity<?> collectionEleves(@PathVariable Long idecole) {
		return eleveService.CollecteEleveses(idecole);
	}

	@GetMapping("/CollectionElevedashbord/{idecole}/{idclasse}/{idannee}")
	public ResponseEntity<?> CollectionElevedashbord(@PathVariable Long idecole, @PathVariable Long idclasse,
			@PathVariable Long idannee) {
		return eleveService.CollecteElevedashbord(idecole, idclasse, idannee);
	}

	@GetMapping("/CollecteAnneeEleve/{idintermedaireclasse}/{idintermedaireannee}")
	public ResponseEntity<?> CollecteAnneeEleve(@PathVariable Long idintermedaireclasse,
			@PathVariable Long idintermedaireannee) {
		return eleveService.CollecteAnneeEleve(idintermedaireclasse, idintermedaireannee);
	}

	@GetMapping("/CollecteClasseAnneeEleve/{idintermedaireclasse}/{idintermedaireannee}")
	public ResponseEntity<?> CollecteClasseAnneeEleve(@PathVariable Long idintermedaireclasse,
			@PathVariable Long idintermedaireannee) {
		return eleveService.CollecteClasseAnneeEleve(idintermedaireclasse, idintermedaireannee);
	}

	@GetMapping("/FicheEleves/{ideleve}")
	public ResponseEntity<?> FicheEleves(@PathVariable Long ideleve) throws FileNotFoundException, JRException {
		return eleveService.FicheEleve(ideleve);
	}
	
	@GetMapping("/searchEleve")
    public ResponseEntity<?> searchEleve(@RequestParam String userRole,
    									  @RequestParam String nom, 
                                          @RequestParam(required = false) Long idecole) {
        boolean isAdmin = "ADMIN".equalsIgnoreCase(userRole);
        
        List<EleveModelDto> collections = eleveService.searchEleves(nom, idecole, isAdmin);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé.");
        }
        return ResponseEntity.ok(collections);
    }

	@GetMapping("/EleveParClasse/{idecole}/{idclasse}/{idannee}")
	public ResponseEntity<?> EleveParClasse(@PathVariable Long idecole, @PathVariable Long idclasse,
			@PathVariable Long idannee) {
		return eleveService.EleveParClasse(idecole, idclasse, idannee);
	}

	@GetMapping("/ElevePar/{idecole}/{idclasse}/{idannee}/{ideleve}")
	public ResponseEntity<?> ElevePar(@PathVariable Long idecole, @PathVariable Long idclasse,
			@PathVariable Long idannee, @PathVariable Long ideleve) {
		return eleveService.ElevePar(idecole, idclasse, idannee, ideleve);
	}

	@GetMapping("/FicheClasse/{idecole}/{idclasse}")
	public ResponseEntity<?> FicheClasse(@PathVariable Long idecole, @PathVariable Long idclasse)
			throws FileNotFoundException, JRException {
		return eleveService.FicheClasse(idecole, idclasse);
	}
	
	 @PostMapping("/effectuer")
	    public ResponseEntity<String> effectuerInscription(@RequestParam String username,
	                                                    @RequestParam String noms,
	                                                    @RequestParam String classe,
	                                                    @RequestParam String ecole,
	                                                    @RequestParam String annee) {
		 eleveService.enregistrerIscription(username, noms, classe, ecole, annee);
	        return ResponseEntity.ok("✅ Inscription enregistré !");
	    }
}

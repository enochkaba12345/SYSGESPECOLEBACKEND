package com.sysgepecole.demo.Controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Models.Annee;
import com.sysgepecole.demo.Service.AnneeService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/annee")
public class AnneeController {
	
	@Autowired
	AnneeService anneeservice;
	
	
	 @GetMapping("/getAllAnnee")
	    public ResponseEntity<?> getAllAnnee()  {
	        return anneeservice.getAllAnnee();
	    }
	 
	 @GetMapping("/CollecteAnnee/{idintermedaireclasse}")
		public ResponseEntity<?> CollecteAnnee(@PathVariable long idintermedaireclasse){
			return anneeservice.CollecteAnnee(idintermedaireclasse);
		}
	 
	 @GetMapping("/CollecteEcoleAnnee/{idecole}")
		public ResponseEntity<?> CollecteEcoleAnnee(@PathVariable long idecole){
			return anneeservice.CollecteEcoleAnnee(idecole);
		}
	 
	 @GetMapping("/CollecteCategorieAnnee/{idtranche}/{idcategorie}/{idecole}")
		public ResponseEntity<?> CollecteCategorieAnnee(@PathVariable long idtranche,@PathVariable long idcategorie,@PathVariable long idecole){
			return anneeservice.CollecteCategorieAnnee(idtranche,idcategorie,idecole);
		}
	 
	 @GetMapping("/CollecteCategorieAnneeAdmin/{idtranche}/{idcategorie}")
		public ResponseEntity<?> CollecteCategorieAnneeAdmin(@PathVariable long idtranche,@PathVariable long idcategorie){
			return anneeservice.CollecteCategorieAnneeAdmin(idtranche,idcategorie);
		}
	
	@PostMapping("/CreateAnnee")
	public Annee CreateClasse(@RequestBody Annee annee) {
		return anneeservice.CreateAnnee(annee);
	}
		

	
	@PutMapping("/updateAnnee/{idannee}")
	public ResponseEntity<Annee> updateAnnee(@PathVariable Long idannee, @RequestBody Annee annee){
		return anneeservice.updateAnnee(idannee, annee);
	}

	@DeleteMapping("/delete/{idannee}")
	public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long idannee){
		return anneeservice.delete(idannee);
	}


}

package com.sysgepecole.demo.Controller;

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
import org.springframework.web.bind.annotation.RestController;


import com.sysgepecole.demo.Dto.IntermedaireAnneeDto;
import com.sysgepecole.demo.Models.Intermedaire_annee;
import com.sysgepecole.demo.Service.Intermedaire_anneeService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/intermedaireannee")
public class Intermedaire_anneeController {
	
	@Autowired
	Intermedaire_anneeService intermedaire_anneeService;
	

    @PostMapping("/create")
    public ResponseEntity<?> createIntermedaireAnnee(@RequestBody IntermedaireAnneeDto requestDto) {
        try {
        	Intermedaire_annee savedIntermedaireAnnee = intermedaire_anneeService.createIntermedaireAnnee(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedIntermedaireAnnee);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la relation intermédiaire: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de la relation intermédiaire");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateIntermedaireAnnee(@RequestBody IntermedaireAnneeDto requestDto) {
        try {
            Intermedaire_annee updatedIntermedaireAnnee = intermedaire_anneeService.updateIntermedaireAnnee(requestDto);
            return ResponseEntity.ok(updatedIntermedaireAnnee);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de la relation intermédiaire: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de la relation intermédiaire");
        }
    }
	
 
	
	  
	    
	    @GetMapping("/getIdInterannee/{idintermedaireannee}")
	    public ResponseEntity<?> getIdInterannee(@PathVariable("idintermedaireannee") long idintermedaireannee) {
	        List<IntermedaireAnneeDto> collections = intermedaire_anneeService.getIdInterannees(idintermedaireannee);
	        if (collections.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun enregistrement trouvé pour cet ID.");
	        } else {
	            return ResponseEntity.ok(collections);
	        }
	    }

	    
	    @GetMapping("/getInterannees")
	    public ResponseEntity<?> getInterannees() {
	        List<IntermedaireAnneeDto> collections = intermedaire_anneeService.getInterannees();

	        if (collections.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun enregistrement trouvé.");
	        } else {
	            return ResponseEntity.ok(collections);
	        }
	    }
	    
	    
	    @GetMapping("/getrannees/{idecole}")
	    public ResponseEntity<?> getrannees(@PathVariable("idecole") long idecole) {
	        List<IntermedaireAnneeDto> collections = intermedaire_anneeService.getrannees(idecole);

	        if (collections.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun enregistrement trouvé.");
	        } else {
	            return ResponseEntity.ok(collections);
	        }
	    }



}

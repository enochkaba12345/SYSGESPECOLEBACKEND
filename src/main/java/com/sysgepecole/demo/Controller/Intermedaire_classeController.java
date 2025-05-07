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


import com.sysgepecole.demo.Dto.IntermedaireClasseDto;
import com.sysgepecole.demo.Models.Intermedaire_classe;
import com.sysgepecole.demo.Service.Intermedaire_classeService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/intermedaireclasse")
public class Intermedaire_classeController {
	
	@Autowired
	Intermedaire_classeService intermedaire_classeService;
	

    @PostMapping("/create")
    public ResponseEntity<?> createIntermedaireClasse(@RequestBody IntermedaireClasseDto requestDto) {
        try {
        	Intermedaire_classe savedIntermedaireClasse = intermedaire_classeService.createIntermedaireClasse(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedIntermedaireClasse);
        } catch (Exception e) {
            System.err.println("Erreur lors de la création de la relation intermédiaire: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de la relation intermédiaire");
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateIntermedaireClasse(@RequestBody IntermedaireClasseDto requestDto) {
        try {
        	Intermedaire_classe updatedIntermedaireClasse = intermedaire_classeService.updateIntermedaireClasse(requestDto);
            return ResponseEntity.ok(updatedIntermedaireClasse);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de la relation intermédiaire: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de la relation intermédiaire");
        }
    }
    
    @GetMapping("/getIdInterclasse/{idintermedaireclasse}")
    public ResponseEntity<?> getIdInterclasse(@PathVariable("idintermedaireclasse") long idintermedaireclasse) {
        List<IntermedaireClasseDto> collections = intermedaire_classeService.getIdInterclasses(idintermedaireclasse);
        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun enregistrement trouvé pour cet ID.");
        } else {
            return ResponseEntity.ok(collections);
        }
    }

    
    @GetMapping("/getInterecoles")
    public ResponseEntity<?> getInterecoles() {
        List<IntermedaireClasseDto> collections = intermedaire_classeService.getInterecoles();

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun enregistrement trouvé.");
        } else {
            return ResponseEntity.ok(collections);
        }
    }
    
    
    @GetMapping("/getInterecoleNoAdmins/{idecole}")
    public ResponseEntity<?> getInterecoleNoAdmins(@PathVariable("idecole") long idecole) {
        List<IntermedaireClasseDto> collections = intermedaire_classeService.getInterecoleNoAdmins(idecole);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun enregistrement trouvé.");
        } else {
            return ResponseEntity.ok(collections);
        }
    }
    
    
    @GetMapping("/getInterniveaux/{idecole}")
    public ResponseEntity<?> getInterniveaux(@PathVariable("idecole") long idecole) {
        List<IntermedaireClasseDto> collections = intermedaire_classeService.getInterniveaux(idecole);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun enregistrement trouvé.");
        } else {
            return ResponseEntity.ok(collections);
        }
    }
    
    @GetMapping("/getniveauclasse/{idecole}/{idniveau}")
    public ResponseEntity<?> getniveauclasse(@PathVariable("idecole") long idecole,@PathVariable("idniveau") long idniveau) {
        List<IntermedaireClasseDto> collections = intermedaire_classeService.getniveauclasses(idecole,idniveau);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun enregistrement trouvé.");
        } else {
            return ResponseEntity.ok(collections);
        }
    }
    
    

}

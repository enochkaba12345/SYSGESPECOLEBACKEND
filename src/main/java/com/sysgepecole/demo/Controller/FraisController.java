package com.sysgepecole.demo.Controller;



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

import com.sysgepecole.demo.Dto.FraisDto;
import com.sysgepecole.demo.Models.Frais;
import com.sysgepecole.demo.Service.FraisService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/frais")
public class FraisController {
	

    @Autowired
    private FraisService fraisService;

    @PostMapping("/createFrais")
    public ResponseEntity<?> createFrais(@RequestBody FraisDto fraisDto) {
    try {
        Frais frais = fraisService.saveFrais(fraisDto);
        return ResponseEntity.ok(frais);
      } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Erreur lors de la création des frais", "error", e.getMessage()));
      }
}

	
    
    @PutMapping("/updateFrais/{idfrais}")
    public ResponseEntity<?> updateFrais(@PathVariable Long idfrais,@RequestBody FraisDto requestDto) {
        try {
        	Frais updateFrais = fraisService.updateFrais(idfrais,requestDto);
            return ResponseEntity.ok(updateFrais);
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour frais: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour frais");
        }
    }
	
    
    @GetMapping("/CollectionTranche")
    public ResponseEntity<?> CollectionTranche()  {
        return fraisService.CollecteTranche();
    }
    
    @GetMapping("/CollectionClasse/{idtranche}")
    public ResponseEntity<?> CollectionClasse(@PathVariable long idtranche)  {
        return fraisService.CollecteClasse(idtranche);
    }

    @GetMapping("/CollecteEcoleTrancheClasse/{idecole}/{idtranche}")
    public ResponseEntity<?> CollecteEcoleTrancheClasse(@PathVariable long idecole,@PathVariable long idtranche)  {
        return fraisService.CollecteEcoleTrancheClasse(idecole,idtranche);
    }
    
    @GetMapping("/CollectionFrais/{idclasse}/{idtranche}")
    public ResponseEntity<?> CollectionFrais(@PathVariable long idclasse,@PathVariable long idtranche)  {
        return fraisService.CollecteFrais(idclasse,idtranche);
    }
    
    @GetMapping("/CollecteFraisClasse/{idecole}/{idclasse}/{idtranche}")
    public ResponseEntity<?> CollecteFraisClasse(@PathVariable long idecole,@PathVariable long idclasse,@PathVariable long idtranche)  {
        return fraisService.CollecteFraisClasse(idecole,idclasse,idtranche);
    }
    
    @GetMapping("/CollectionEcoleTranche/{idecole}")
    public ResponseEntity<?> CollectionEcoleTranche(@PathVariable long idecole)  {
        return fraisService.CollecteEcoleTranche(idecole);
    }
   
}

package com.sysgepecole.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.sysgepecole.demo.Models.Classe;
import com.sysgepecole.demo.Service.ClasseService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/classe")
public class ClasseController {
	
	
	@Autowired
	ClasseService classeservice;
	
	
	@GetMapping("/findClasseByClasse/{classe}")
	public Optional<Classe> findClasseByClasse(@PathVariable String classe){
		return classeservice.findClasseByClasse(classe);
	}
	
	@GetMapping("/CollecteClasse/{idniveau}/{idecole}")
	public ResponseEntity<?> CollecteClasse(@PathVariable long idniveau,@PathVariable long idecole){
		return classeservice.CollecteClasse(idniveau,idecole);
	}
	
	@GetMapping("/CollecteEcole/{idecole}")
	public ResponseEntity<?> CollecteEcole(@PathVariable long idecole){
		return classeservice.CollecteEcole(idecole);
	}
	
	@GetMapping("/CollecteEcoleAnnee/{idecole}/{idannee}")
	public ResponseEntity<?> CollecteEcoleAnnee(@PathVariable long idecole,@PathVariable long idannee){
		return classeservice.CollecteEcoleAnnee(idecole,idannee);
	}
	
	@GetMapping("/CollecteCategorieClasse/{idtranche}/{idcategorie}/{idecole}")
	public ResponseEntity<?> CollecteCategorieClasse(@PathVariable long idtranche,@PathVariable long idcategorie,@PathVariable long idecole){
		return classeservice.CollecteCategorieClasse(idtranche,idcategorie,idecole);
	}
	
	@GetMapping("/CollecteCategorieClasseAdmin/{idtranche}/{idcategorie}")
	public ResponseEntity<?> CollecteCategorieClasseAdmin(@PathVariable long idtranche,@PathVariable long idcategorie){
		return classeservice.CollecteCategorieClasseAdmin(idtranche,idcategorie);
	}
	
	@GetMapping("/getAllClasse")
	public List<Classe> getAllClasse(){
		return classeservice.getAllClasse();
	}
	
	@PostMapping("/createClasses")
	public Classe createClasses(@RequestBody Classe classe) {
		return classeservice.createClasses(classe);
	}
	
	@PutMapping("/updateClasses/{idclasse}")
	public ResponseEntity<Classe> updateClasses(@PathVariable Long idclasse, @RequestBody Classe classeDetails){
		return classeservice.updateClasses(idclasse, classeDetails);
	}
	


}

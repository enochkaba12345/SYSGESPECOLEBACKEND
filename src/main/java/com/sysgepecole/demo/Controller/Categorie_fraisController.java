package com.sysgepecole.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Service.Categorie_fraisService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categorie")
public class Categorie_fraisController {
	
	@Autowired
	Categorie_fraisService categorieservice;
	
	 @GetMapping("/getAllCategorie")
	    public ResponseEntity<?> getAllCategorie()  {
	        return categorieservice.getAllCategorie();
	    }
	 
	  @GetMapping("/CollecteTrancheCategorie/{idtranche}")
	    public ResponseEntity<?> CollecteTrancheCategorie(@PathVariable Long idtranche) {
	        return categorieservice.CollecteTrancheCategorie(idtranche);
	    }

}

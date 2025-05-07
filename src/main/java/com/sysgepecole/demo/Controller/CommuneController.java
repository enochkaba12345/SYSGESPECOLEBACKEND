package com.sysgepecole.demo.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Models.Commune;
import com.sysgepecole.demo.Service.CommuneService;




@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/commune")
public class CommuneController {
	
	@Autowired
	CommuneService communeservice;
	
	@GetMapping("/getAllCommune")
	public List<Commune> getAllCommune() {
		return communeservice.getAllCommune();
	}
	
	 @GetMapping("/getcommuneByIdprovince/{idprovince}")
	    public ResponseEntity<?> getcommuneByIdprovince(@PathVariable Long idprovince)  {
	        return communeservice.getcommuneByIdprovince(idprovince);
	    }

}

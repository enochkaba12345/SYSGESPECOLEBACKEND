package com.sysgepecole.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Service.NiveauService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/niveau")
public class NiveauContoller {

	
	@Autowired
	NiveauService niveauservice;
	
	 @GetMapping("/getAllNiveau")
	    public ResponseEntity<?> getAllNiveau()  {
	        return niveauservice.getAllNiveau();
	    }
}

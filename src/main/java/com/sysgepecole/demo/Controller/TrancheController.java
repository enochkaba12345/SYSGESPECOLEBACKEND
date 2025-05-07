package com.sysgepecole.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Service.TrancheService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tranche")
public class TrancheController {
	
	@Autowired
	TrancheService trancheservice;
	
	 @GetMapping("/getAllTranche")
	    public ResponseEntity<?> getAllTranche()  {
	        return trancheservice.getAllTranche();
	    }
	 
	 
	
	

}

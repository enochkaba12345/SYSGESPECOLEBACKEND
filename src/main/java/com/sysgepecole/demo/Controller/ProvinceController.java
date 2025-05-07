package com.sysgepecole.demo.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.sysgepecole.demo.Service.ProvinceService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/province")
public class ProvinceController {
	

	@Autowired
	ProvinceService provinceservice;
	
	 @GetMapping("/getAllProvince")
	    public ResponseEntity<?> getAllProvince()  {
		 System.out.println("bonjour");
	        return provinceservice.getAllProvince();
	    }
	


}

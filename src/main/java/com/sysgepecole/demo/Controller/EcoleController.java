package com.sysgepecole.demo.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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


import com.sysgepecole.demo.Models.Ecole;
import com.sysgepecole.demo.Service.EcoleService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ecole")
public class EcoleController {
	
	@Autowired
	EcoleService ecoleservice;
	
	
	@GetMapping("/findEcoleByEcole/{ecole}")
	public Optional<Ecole> findEcoleByEcole(@PathVariable String ecole){
		return ecoleservice.findEcoleByEcole(ecole);
	}

	@GetMapping("/getAllEcole")
	public List<Ecole> getAllEcole(){
		return ecoleservice.getAllEcole();
	}
	
	@PostMapping("/createEcoles")
	public Ecole createEcoles(@RequestBody Ecole ecole) {
		return ecoleservice.createEcoles(ecole);
	}
	
	

	@PutMapping("/updateEcoles/{idecole}")
	public ResponseEntity<Ecole> updateEcoles(@PathVariable Long idecole, @RequestBody Ecole ecoleDetails){
		return ecoleservice.updateEcoles(idecole, ecoleDetails);
	}
		

	@DeleteMapping("/delete/{idecole}")
	public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long idecole){
		return ecoleservice.delete(idecole);
	}


	 @GetMapping("/CollectionEcole")
	    public ResponseEntity<?> CollectionEcole()  {
	        return ecoleservice.CollectionEcole();
	    }
	 
	 @GetMapping("/CollectionEcoles/{idecole}")
	    public ResponseEntity<?> CollectionEcoles(@PathVariable long idecole)  {
	        return ecoleservice.CollectionEcoles(idecole);
	    }
	 
	 @GetMapping("/getEcole/{idecole}")
	    public ResponseEntity<?> getEcole(@PathVariable long idecole)  {
	        return ecoleservice.getEcole(idecole);
	    }
	 
	 
}

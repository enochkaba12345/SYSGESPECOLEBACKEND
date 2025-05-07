package com.sysgepecole.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Service.RoleService;
import com.sysgepecole.demo.ServiceImpl.RoleServiceImpl;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/role")
public class RoleController {
	
	
	@Autowired
	RoleService roleservice;
	
	@Autowired
	RoleServiceImpl rolesservice;
	
	 @GetMapping("/getAllRole")
	    public ResponseEntity<?> getAllRole()  {
	        return roleservice.getAllRole();
	    }

	 @GetMapping
	    public List<String> getUserRoles() {
	        return rolesservice.getUserRoles();
	    }
	 
	 @GetMapping("/getRole/{role}")
	    public ResponseEntity<?> getRole(@PathVariable String role)  {
	        return roleservice.getRole(role);
	    }
}

package com.sysgepecole.demo.Controller;

import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Dto.UserModelDto;
import com.sysgepecole.demo.Models.Users;
import com.sysgepecole.demo.Service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PutMapping("/updateUserIdentique/{iduser}")
	public ResponseEntity<Users> updateUserIdentique(@PathVariable Long iduser, @RequestBody Users user) {
		return userService.updateUserIdentique(iduser, user);
	}
	
	
	@PutMapping("/updateUser/{iduser}")
    public ResponseEntity<Users> updateUser(@PathVariable Long iduser,@RequestBody Users user) {
        return userService.updateUser(iduser, user); 
    }
	
	
	 @GetMapping("/collectUsers")
	    public ResponseEntity<?> collectUsers(@RequestParam String userRole,@RequestParam(required = false) Long idecole) {
	        return userService.collectUsers(userRole, idecole);
	    }

	@GetMapping("/searchUser")
	public ResponseEntity<?> searchUser(@RequestParam String username) {
		return userService.searchUser(username);
	}
	   
	   @PutMapping("/updatePassword/{iduser}")
	    public ResponseEntity<String> updatePassword(@PathVariable Long iduser) {
	        try {
	            String response = userService.updatePassword(iduser);
	            return ResponseEntity.ok(response);
	        } catch (RuntimeException ex) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	        }
	    }
	   @PutMapping("/updateStatus/{iduser}/{statut}")
	   public ResponseEntity<String> updateStatus(@PathVariable Long iduser,@PathVariable Boolean statut) {
			    try {
			        String response = userService.updateStatus(iduser, statut); 
			        return ResponseEntity.ok(response); 
			    } catch (RuntimeException ex) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			    }
	   }
	   
	   @PutMapping("/updatesPassword/{iduser}/{password}")
	   public ResponseEntity<String> updatesPassword(@PathVariable Long iduser,@PathVariable String password) {
	       try {
	           String response = userService.updatesPassword(iduser, password);
	           return ResponseEntity.ok(response);
	       } catch (RuntimeException ex) {
	           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	       }
	   }

}

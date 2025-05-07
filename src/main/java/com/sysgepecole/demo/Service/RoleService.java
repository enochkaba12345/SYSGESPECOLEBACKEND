package com.sysgepecole.demo.Service;

import org.springframework.http.ResponseEntity;

public interface RoleService {

	 ResponseEntity<?> getAllRole();
	 ResponseEntity<?> getRole(String role);
}

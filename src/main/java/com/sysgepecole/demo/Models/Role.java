package com.sysgepecole.demo.Models;



import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_role")
public class Role {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long idrole;

	 
	    private String role;

	  

		public Long getIdrole() {
			return idrole;
		}

		public void setIdrole(Long idrole) {
			this.idrole = idrole;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		

		
}

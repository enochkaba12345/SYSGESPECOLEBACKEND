package com.sysgepecole.demo.Dto;

import java.util.List;

public class UserDTO {

	
	 private Long id;
	    private String username;
	    private List<RoleDto> role;
	    
	    
		public UserDTO(Long id, String username, List<RoleDto> role) {
			this.id = id;
			this.username = username;
			this.role = role;
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public List<RoleDto> getRole() {
			return role;
		}


		public void setRole(List<RoleDto> role) {
			this.role = role;
		}
	    
	    
	    
}

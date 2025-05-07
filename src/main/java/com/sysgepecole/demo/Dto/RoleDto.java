package com.sysgepecole.demo.Dto;

public class RoleDto {

	 private Long idrole;
	 private String role;
	 
	
	    public RoleDto() {
	    }

	    public RoleDto(Long idrole,String role) {
	        this.idrole = idrole;
	        this.role = role;
	    }

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

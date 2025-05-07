package com.sysgepecole.demo.Dto;

import java.util.ArrayList;
import java.util.List;

import com.sysgepecole.demo.Models.Role;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public class UserModelDto {

	private Long iduser;
	private String nom;
	private String postnom;
	private String prenom;
	private String username;
	private String password;
	private Long idecole;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private Boolean statut;
	private String email;
	private String telephone;
	
    private List<String> role;
    private List<Long> idrole;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPostnom() {
		return postnom;
	}
	public void setPostnom(String postnom) {
		this.postnom = postnom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Boolean getStatut() {
		return statut;
	}
	public void setStatut(Boolean statut) {
		this.statut = statut;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	  public List<String> getRole() {
	        System.out.println("getIdrole() appelé, idrole = " + role);
	        return role;
	    }

	    public void setRole(List<String> role) {
	        this.role = role;
	    }

	 
	public UserModelDto() {
		this.idrole = new ArrayList<>();
	}
	
	  public UserModelDto(List<Long> idrole) {
	        this.idrole = idrole;
	    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public Long getIduser() {
		return iduser;
	}
	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}
	
	
	public List<Long> getIdrole() {
		return idrole;
	}
	public void setIdrole(List<Long> idrole) {
		this.idrole = idrole;
	}
	public UserModelDto(Long iduser,String nom, String postnom, String prenom, String username,String password,Long idecole, Boolean statut, String email,
			String telephone, List<String> role, List<Long> idrole) {
		super();
		this.iduser = iduser;
		this.nom = nom;
		this.postnom = postnom;
		this.prenom = prenom;
		this.username = username;
		this.idecole = idecole;
		this.password = password;
		this.statut = statut;
		this.email = email;
		this.telephone = telephone;
		this.role = role;
		this.idrole = idrole;
	}
	
	
	public Long getIdecole() {
		return idecole;
	}
	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}

	@OneToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
	
}

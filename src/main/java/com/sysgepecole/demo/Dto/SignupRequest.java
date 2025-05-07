package com.sysgepecole.demo.Dto;

import java.util.ArrayList;
import java.util.List;

public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private String telephone;
    private String nom;
    private String postnom;
    private String prenom;
    private Boolean statut;
    private Long iduser;
    private Long idecole;
    private List<Long> idrole;
   

  
   
    public SignupRequest() {
        this.idrole = new ArrayList<>();
    }

 
    public SignupRequest(List<Long> idrole) {
        this.idrole = idrole;
    }
   
 
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

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

    public List<Long> getIdrole() {
        System.out.println("getIdrole() appelé, idrole = " + idrole);
        return idrole;
    }

    public void setIdrole(List<Long> idrole) {
        this.idrole = idrole;
    }


    
	public Long getIduser() {
		return iduser;
	}


	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}


	@Override
	public String toString() {
		return "SignupRequest [username=" + username + ", email=" + email + ", password=" + password + ", telephone="
				+ telephone + ", nom=" + nom + ", postnom=" + postnom + ", prenom=" + prenom + ", statut=" + statut
				+ ", iduser=" + iduser + ", idecole=" + idecole + ", idrole=" + idrole + "]";
	}


	public Long getIdecole() {
		return idecole;
	}


	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}
  

  
}

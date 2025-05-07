package com.sysgepecole.demo.Models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tab_eleve")
public class Eleve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ideleve;
	
	private String nom;
	private String postnom;
	private String prenom;
	private String sexe;
	private String adresse;
	private String nomtuteur;
	private String email;
	private String telephone;
	private Date dateins=new Date();
	private Date datenaiss;
	private Long idprovince;
	private Long idintermedaireclasse;
	private Long idintermedaireannee;
	private Long iduser;
	public long getIdeleve() {
		return ideleve;
	}
	public void setIdeleve(long ideleve) {
		this.ideleve = ideleve;
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
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getNomtuteur() {
		return nomtuteur;
	}
	public void setNomtuteur(String nomtuteur) {
		this.nomtuteur = nomtuteur;
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
	public Date getDateins() {
		return dateins;
	}
	public void setDateins(Date dateins) {
		this.dateins = dateins;
	}
	public Date getDatenaiss() {
		return datenaiss;
	}
	public void setDatenaiss(Date datenaiss) {
		this.datenaiss = datenaiss;
	}
	public Long getIdprovince() {
		return idprovince;
	}
	public void setIdprovince(Long idprovince) {
		this.idprovince = idprovince;
	}

	public Long getIdintermedaireannee() {
		return idintermedaireannee;
	}
	public void setIdintermedaireannee(Long idintermedaireannee) {
		this.idintermedaireannee = idintermedaireannee;
	}
	
	public Long getIduser() {
		return iduser;
	}
	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}
	public Long getIdintermedaireclasse() {
		return idintermedaireclasse;
	}
	public void setIdintermedaireclasse(Long idintermedaireclasse) {
		this.idintermedaireclasse = idintermedaireclasse;
	}
	
	
	

}

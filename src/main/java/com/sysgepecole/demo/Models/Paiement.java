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
@Table(name="tab_paiement")
public class Paiement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idpaiement;
	
	private String frais;
	private String categorie;
	private String Statut;
	public String getFrais() {
		return frais;
	}
	public void setFrais(String frais) {
		this.frais = frais;
	}
	private Long idintermedaireclasse;
	private Long idintermedaireannee;
	private Double montants;
	private long ideleve;
	private Date datepaie=new Date();
	private Long iduser;
	public long getIdpaiement() {
		return idpaiement;
	}
	public void setIdpaiement(long idpaiement) {
		this.idpaiement = idpaiement;
	}

	public Long getIdintermedaireclasse() {
		return idintermedaireclasse;
	}
	public void setIdintermedaireclasse(Long idintermedaireclasse) {
		this.idintermedaireclasse = idintermedaireclasse;
	}
	public Long getIdintermedaireannee() {
		return idintermedaireannee;
	}
	public void setIdintermedaireannee(Long idintermedaireannee) {
		this.idintermedaireannee = idintermedaireannee;
	}
	
	public long getIdeleve() {
		return ideleve;
	}
	public void setIdeleve(long ideleve) {
		this.ideleve = ideleve;
	}
	public Date getDatepaie() {
		return datepaie;
	}
	public void setDatepaie(Date datepaie) {
		this.datepaie = datepaie;
	}
	
	public Long getIduser() {
		return iduser;
	}
	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}
	public Double getMontants() {
		return montants;
	}
	public void setMontants(Double montants) {
		this.montants = montants;
	}
	public String getStatut() {
		return Statut;
	}
	public void setStatut(String statut) {
		Statut = statut;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	
	
}

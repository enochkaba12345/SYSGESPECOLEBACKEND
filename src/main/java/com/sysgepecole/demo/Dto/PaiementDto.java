package com.sysgepecole.demo.Dto;

import java.util.Date;

public class PaiementDto {
	
	private Long idpaiement;
	private Double montants;
	private Date datepaie;
	private String frais;
	private String noms;
	private Double montant_frais;
	private Double montant_paiement;
	
	private Long id;
    private String photo;
    
	public String getFrais() {
		return frais;
	}
	public void setFrais(String frais) {
		this.frais = frais;
	}
	private Long ideleve;
	private String nom;
	private String postnom;
	private String prenom;
	private String sexe;
	private String adresse;
	private String telephone;
	
	private Long idecole;
	private String ecole;
	
	private String logos;
	
	private Long idclasse;
	private String classe;

	private Long idannee;
	private String annee;
	
	
	private Long idprovince;
	private String province;
	
	private String avenue;
	private Long idcommune;
	private String commune;
	
	private Long iduser;
	private String username;

    private Long idintermedaireclasse;
    private Long idintermedaireannee;
    
    private Long idtranche;
	private String tranche;
	
	private Long idcategorie;
	private String categorie;
	
	private Long idfrais;
	private Double montant;
	public Long getIdpaiement() {
		return idpaiement;
	}
	public void setIdpaiement(Long idpaiement) {
		this.idpaiement = idpaiement;
	}
	public Double getMontants() {
		return montants;
	}
	public void setMontants(Double montants) {
		this.montants = montants;
	}
	public Date getDatepaie() {
		return datepaie;
	}
	public void setDatepaie(Date datepaie) {
		this.datepaie = datepaie;
	}
	public Long getIdeleve() {
		return ideleve;
	}
	public void setIdeleve(Long ideleve) {
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
	public Long getIdecole() {
		return idecole;
	}
	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}
	public String getEcole() {
		return ecole;
	}
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	public Long getIdclasse() {
		return idclasse;
	}
	public void setIdclasse(Long idclasse) {
		this.idclasse = idclasse;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public Long getIdannee() {
		return idannee;
	}
	public void setIdannee(Long idannee) {
		this.idannee = idannee;
	}
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	public Long getIdprovince() {
		return idprovince;
	}
	public void setIdprovince(Long idprovince) {
		this.idprovince = idprovince;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAvenue() {
		return avenue;
	}
	public void setAvenue(String avenue) {
		this.avenue = avenue;
	}
	public Long getIdcommune() {
		return idcommune;
	}
	public void setIdcommune(Long idcommune) {
		this.idcommune = idcommune;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
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
	public Long getIdtranche() {
		return idtranche;
	}
	public void setIdtranche(Long idtranche) {
		this.idtranche = idtranche;
	}
	public String getTranche() {
		return tranche;
	}
	public void setTranche(String tranche) {
		this.tranche = tranche;
	}
	public Long getIdcategorie() {
		return idcategorie;
	}
	public void setIdcategorie(Long idcategorie) {
		this.idcategorie = idcategorie;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public Long getIdfrais() {
		return idfrais;
	}
	public void setIdfrais(Long idfrais) {
		this.idfrais = idfrais;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
	public Long getIduser() {
		return iduser;
	}
	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Double getMontant_frais() {
		return montant_frais;
	}
	public void setMontant_frais(Double montant_frais) {
		this.montant_frais = montant_frais;
	}
	public Double getMontant_paiement() {
		return montant_paiement;
	}
	public void setMontant_paiement(Double montant_paiement) {
		this.montant_paiement = montant_paiement;
	}
	public String getNoms() {
		return noms;
	}
	public void setNoms(String noms) {
		this.noms = noms;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogos() {
		return logos;
	}
	public void setLogos(String logos) {
		this.logos = logos;
	}
	@Override
	public String toString() {
		return "PaiementDto [idpaiement=" + idpaiement + ", montants=" + montants + ", datepaie=" + datepaie
				+ ", frais=" + frais + ", noms=" + noms + ", montant_frais=" + montant_frais + ", montant_paiement="
				+ montant_paiement + ", id=" + id + ", photo=" + photo + ", ideleve=" + ideleve + ", nom=" + nom
				+ ", postnom=" + postnom + ", prenom=" + prenom + ", sexe=" + sexe + ", adresse=" + adresse
				+ ", telephone=" + telephone + ", idecole=" + idecole + ", ecole=" + ecole + ", logos=" + logos
				+ ", idclasse=" + idclasse + ", classe=" + classe + ", idannee=" + idannee + ", annee=" + annee
				+ ", idprovince=" + idprovince + ", province=" + province + ", avenue=" + avenue + ", idcommune="
				+ idcommune + ", commune=" + commune + ", iduser=" + iduser + ", username=" + username
				+ ", idintermedaireclasse=" + idintermedaireclasse + ", idintermedaireannee=" + idintermedaireannee
				+ ", idtranche=" + idtranche + ", tranche=" + tranche + ", idcategorie=" + idcategorie + ", categorie="
				+ categorie + ", idfrais=" + idfrais + ", montant=" + montant + "]";
	}
    
	
	
    
}

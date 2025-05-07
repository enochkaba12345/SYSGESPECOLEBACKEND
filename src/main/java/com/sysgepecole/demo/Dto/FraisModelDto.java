package com.sysgepecole.demo.Dto;

public class FraisModelDto {
	
	private String tranche;
    private String classe;
    private String categorie;
    private Long idtranche;
    private Long idcategorie;
    private Long idintermedaireclasse;
    private Long idintermedaireannee;
    private Long idfrais;
    private Double montant;
    private String annee;
    private Long idclasse;
    public Long getIdfrais() {
		return idfrais;
	}
	public void setIdfrais(Long idfrais) {
		this.idfrais = idfrais;
	}

	private Long idannee;
    private String ecole;
    private Long idecole;
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	public Long getIdclasse() {
		return idclasse;
	}
	public String getEcole() {
		return ecole;
	}
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	public Long getIdecole() {
		return idecole;
	}
	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}
	public void setIdclasse(Long idclasse) {
		this.idclasse = idclasse;
	}
	public Long getIdannee() {
		return idannee;
	}
	public void setIdannee(Long idannee) {
		this.idannee = idannee;
	}
	public String getTranche() {
		return tranche;
	}
	public void setTranche(String tranche) {
		this.tranche = tranche;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getCategorie() {
		return categorie;
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
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public Long getIdtranche() {
		return idtranche;
	}
	public void setIdtranche(Long idtranche) {
		this.idtranche = idtranche;
	}
	public Long getIdcategorie() {
		return idcategorie;
	}
	public void setIdcategorie(Long idcategorie) {
		this.idcategorie = idcategorie;
	}
	public Double getMontant() {
		return montant;
	}
	public void setMontant(Double montant) {
		this.montant = montant;
	}
    
public FraisModelDto() {
		
	}
    

}

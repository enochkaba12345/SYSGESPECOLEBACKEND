package com.sysgepecole.demo.Dto;

public class AnneeModelDto {

	private String ecole;
    private String annee;
    private Long idecole;
    private Long idannee;
    private long idintermedaireannee;
	public long getIdintermedaireannee() {
		return idintermedaireannee;
	}
	public void setIdintermedaireannee(long idintermedaireannee) {
		this.idintermedaireannee = idintermedaireannee;
	}
	public String getEcole() {
		return ecole;
	}
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	public Long getIdecole() {
		return idecole;
	}
	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}
	public Long getIdannee() {
		return idannee;
	}
	public void setIdannee(Long idannee) {
		this.idannee = idannee;
	}
	
    
public AnneeModelDto() {
		
	}
}

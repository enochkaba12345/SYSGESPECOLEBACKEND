package com.sysgepecole.demo.Dto;

public class IntermedaireAnneeDto {
	
	private long idintermedaireannee;
	private long iduser;
	public long getIdintermedaireannee() {
		return idintermedaireannee;
	}
	public void setIdintermedaireannee(long idintermedaireannee) {
		this.idintermedaireannee = idintermedaireannee;
	}
	private EcoleDto ecoleDto;
    private AnneeDto anneeDto;
	public EcoleDto getEcoleDto() {
		return ecoleDto;
	}
	public void setEcoleDto(EcoleDto ecoleDto) {
		this.ecoleDto = ecoleDto;
	}
	public AnneeDto getAnneeDto() {
		return anneeDto;
	}
	public void setAnneeDto(AnneeDto anneeDto) {
		this.anneeDto = anneeDto;
	}
    
	private long idannee;
    private long idecole;
	public long getIdannee() {
		return idannee;
	}
	public void setIdannee(long idannee) {
		this.idannee = idannee;
	}
	public long getIdecole() {
		return idecole;
	}
	public void setIdecole(long idecole) {
		this.idecole = idecole;
	}
	public long getIduser() {
		return iduser;
	}
	public void setIduser(long iduser) {
		this.iduser = iduser;
	}
    
	    
}

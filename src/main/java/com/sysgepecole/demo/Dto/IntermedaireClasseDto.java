package com.sysgepecole.demo.Dto;

public class IntermedaireClasseDto {

	private long idintermedaireclasse;
	private long iduser;
	private EcoleDto ecoleDto;
    private ClasseDto classeDto;
	public long getIdintermedaireclasse() {
		return idintermedaireclasse;
	}
	public void setIdintermedaireclasse(long idintermedaireclasse) {
		this.idintermedaireclasse = idintermedaireclasse;
	}
	public EcoleDto getEcoleDto() {
		return ecoleDto;
	}
	public void setEcoleDto(EcoleDto ecoleDto) {
		this.ecoleDto = ecoleDto;
	}
	public ClasseDto getClasseDto() {
		return classeDto;
	}
	public void setClasseDto(ClasseDto classeDto) {
		this.classeDto = classeDto;
	}
    
	private long idclasse;
    private long idecole;
	public long getIdclasse() {
		return idclasse;
	}
	public void setIdclasse(long idclasse) {
		this.idclasse = idclasse;
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

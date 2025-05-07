package com.sysgepecole.demo.Dto;

public class ClasseModelDto {

	
	private String ecole;
    private String classe;
    private Long idecole;
    private Long idclasse;
    private long idintermedaireclasse;
    
    public ClasseModelDto(String classe, Long idclasse, long idniveau, String niveau) {
		super();
		this.classe = classe;
		this.idclasse = idclasse;
		this.idniveau = idniveau;
		this.niveau = niveau;
	}
	public Long getIdclasse() {
		return idclasse;
	}
	public void setIdclasse(Long idclasse) {
		this.idclasse = idclasse;
	}
	public long getIdintermedaireclasse() {
		return idintermedaireclasse;
	}
	public void setIdintermedaireclasse(long idintermedaireclasse) {
		this.idintermedaireclasse = idintermedaireclasse;
	}
	private long idniveau;
    private String niveau;
    @Override
	public String toString() {
		return "ClasseModelDto [ecole=" + ecole + ", classe=" + classe + ", idecole=" + idecole + ", idclasse="
				+ idclasse + ", idniveau=" + idniveau + ", niveau=" + niveau + ", classeDto=" + classeDto + "]";
	}
	public long getIdniveau() {
		return idniveau;
	}
	public void setIdniveau(long idniveau) {
		this.idniveau = idniveau;
	}
	public String getNiveau() {
		return niveau;
	}
	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}
	private ClasseDto classeDto;
	public String getEcole() {
		return ecole;
	}
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public Long getIdecole() {
		return idecole;
	}
	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}
	public ClasseDto getClasseDto() {
		return classeDto;
	}
	public void setClasseDto(ClasseDto classeDto) {
		this.classeDto = classeDto;
	}
    
	public ClasseModelDto() {
		
	}	
    
}

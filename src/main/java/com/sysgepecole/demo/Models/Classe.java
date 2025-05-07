package com.sysgepecole.demo.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tab_classe")
public class Classe {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idclasse;

	private String classe;
	private Long iduser;
	private long idniveau;

	public long getIdniveau() {
		return idniveau;
	}

	public void setIdniveau(long idniveau) {
		this.idniveau = idniveau;
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

	public Long getIduser() {
		return iduser;
	}

	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}



	
	

}

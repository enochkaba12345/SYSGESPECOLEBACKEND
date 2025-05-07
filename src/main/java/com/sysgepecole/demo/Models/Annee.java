package com.sysgepecole.demo.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_annee")
public class Annee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idannee;

	private String annee;
	private long iduser;

	public long getIdannee() {
		return idannee;
	}
	public void setIdannee(long idannee) {
		this.idannee = idannee;
	}
	public String getAnnee() {
		return annee;
	}
	public void setAnnee(String annee) {
		this.annee = annee;
	}
	public long getIduser() {
		return iduser;
	}
	public void setIduser(long iduser) {
		this.iduser = iduser;
	}

	
	

}

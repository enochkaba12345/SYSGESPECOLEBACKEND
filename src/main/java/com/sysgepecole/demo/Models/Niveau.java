package com.sysgepecole.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tab_niveau")
public class Niveau {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idniveau;
	
	private String niveau;

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
	
	

}

package com.sysgepecole.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tab_logos")
public class Logos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String logos;
	private Long idecole;
	private Long iduser;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public Long getIdecole() {
		return idecole;
	}
	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}
	public Long getIduser() {
		return iduser;
	}
	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}
	public String getLogos() {
		return logos;
	}
	public void setLogos(String logos) {
		this.logos = logos;
	}
	
	
	

}

package com.sysgepecole.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tab_commune")
public class Commune {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idcommune;

	private String commune;
	private Long idprovince;
	public long getIdcommune() {
		return idcommune;
	}
	public void setIdcommune(long idcommune) {
		this.idcommune = idcommune;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public Long getIdprovince() {
		return idprovince;
	}
	public void setIdprovince(Long idprovince) {
		this.idprovince = idprovince;
	}
	
	

}

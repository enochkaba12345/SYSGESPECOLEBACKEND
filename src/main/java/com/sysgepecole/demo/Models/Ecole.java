package com.sysgepecole.demo.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tab_ecole")
public class Ecole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idecole;

	private String ecole;
	private Long idprovince;
	private Long idcommune;
	private String avenue;
	private Long iduser;
	public long getIdecole() {
		return idecole;
	}
	public void setIdecole(long idecole) {
		this.idecole = idecole;
	}
	public String getEcole() {
		return ecole;
	}
	public void setEcole(String ecole) {
		this.ecole = ecole;
	}
	public Long getIdprovince() {
		return idprovince;
	}
	public void setIdprovince(Long idprovince) {
		this.idprovince = idprovince;
	}
	public Long getIdcommune() {
		return idcommune;
	}
	public void setIdcommune(Long idcommune) {
		this.idcommune = idcommune;
	}
	public String getAvenue() {
		return avenue;
	}
	public void setAvenue(String avenue) {
		this.avenue = avenue;
	}
	public Long getIduser() {
		return iduser;
	}
	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}

	

}

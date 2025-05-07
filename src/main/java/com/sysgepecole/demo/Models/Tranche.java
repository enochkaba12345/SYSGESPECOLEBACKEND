package com.sysgepecole.demo.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_tranche")
public class Tranche {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idtranche;

	private String tranche;

	public long getIdtranche() {
		return idtranche;
	}
	public void setIdtranche(long idtranche) {
		this.idtranche = idtranche;
	}
	public String getTranche() {
		return tranche;
	}
	public void setTranche(String tranche) {
		this.tranche = tranche;
	}

	
	

}

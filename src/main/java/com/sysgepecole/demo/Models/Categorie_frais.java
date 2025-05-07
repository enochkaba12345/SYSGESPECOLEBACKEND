package com.sysgepecole.demo.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_categoriefrais")
public class Categorie_frais {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idcategorie;

	private String categorie;

	public long getIdcategorie() {
		return idcategorie;
	}
	public void setIdcategorie(long idcategorie) {
		this.idcategorie = idcategorie;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	
	

}

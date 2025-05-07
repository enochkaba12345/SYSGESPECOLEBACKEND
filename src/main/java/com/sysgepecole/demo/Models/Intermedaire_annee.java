package com.sysgepecole.demo.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_intermedaireannee")
public class Intermedaire_annee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idintermedaireannee;

	
	@ManyToOne
    @JoinColumn(name = "idecole")
	    private Ecole ecole;

	@ManyToOne
    @JoinColumn(name = "idannee")
	    private Annee annee;

	public long getIdintermedaireannee() {
		return idintermedaireannee;
	}

	public void setIdintermedaireannee(long idintermedaireannee) {
		this.idintermedaireannee = idintermedaireannee;
	}

	public Ecole getEcole() {
		return ecole;
	}

	public void setEcole(Ecole ecole) {
		this.ecole = ecole;
	}

	public Annee getAnnee() {
		return annee;
	}

	public void setAnnee(Annee annee) {
		this.annee = annee;
	}

	
	    
	
	
	

}

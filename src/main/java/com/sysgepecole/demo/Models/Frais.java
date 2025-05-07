package com.sysgepecole.demo.Models;



import jakarta.persistence.Column;
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
@Table(name = "Tab_frais")
public class Frais {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idfrais;

	private Long iduser;

	
	@Column(name = "montant")
    private Double montant;

    @ManyToOne
    @JoinColumn(name = "idtranche")
    private Tranche tranche;

    @ManyToOne
    @JoinColumn(name = "idcategorie")
    private Categorie_frais categorie;

    @ManyToOne
    @JoinColumn(name = "idintermedaireannee")
    private Intermedaire_annee intermedaireAnnee;

    @ManyToOne
    @JoinColumn(name = "idintermedaireclasse")
    private Intermedaire_classe intermedaireClasse;

	public long getIdfrais() {
		return idfrais;
	}

	public void setIdfrais(long idfrais) {
		this.idfrais = idfrais;
	}

	

	public Long getIduser() {
		return iduser;
	}

	public void setIduser(Long iduser) {
		this.iduser = iduser;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Tranche getTranche() {
		return tranche;
	}

	public void setTranche(Tranche tranche) {
		this.tranche = tranche;
	}

	public Categorie_frais getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie_frais categorie) {
		this.categorie = categorie;
	}

	public Intermedaire_annee getIntermedaireAnnee() {
		return intermedaireAnnee;
	}

	public void setIntermedaireAnnee(Intermedaire_annee intermedaireAnnee) {
		this.intermedaireAnnee = intermedaireAnnee;
	}

	public Intermedaire_classe getIntermedaireClasse() {
		return intermedaireClasse;
	}

	public void setIntermedaireClasse(Intermedaire_classe intermedaireClasse) {
		this.intermedaireClasse = intermedaireClasse;
	}
	
	
    

}

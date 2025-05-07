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
@Table(name = "Tab_intermedaireclasse")
public class Intermedaire_classe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idintermedaireclasse;


	@ManyToOne
    @JoinColumn(name = "idecole")
	    private Ecole ecole;

	@ManyToOne
    @JoinColumn(name = "idclasse")
	    private Classe classe;

		public long getIdintermedaireclasse() {
			return idintermedaireclasse;
		}

		public void setIdintermedaireclasse(long idintermedaireclasse) {
			this.idintermedaireclasse = idintermedaireclasse;
		}

		public Ecole getEcole() {
			return ecole;
		}

		public void setEcole(Ecole ecole) {
			this.ecole = ecole;
		}

		public Classe getClasse() {
			return classe;
		}

		public void setClasse(Classe classe) {
			this.classe = classe;
		}
	
	

}

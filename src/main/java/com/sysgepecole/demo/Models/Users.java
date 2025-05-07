package com.sysgepecole.demo.Models;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_user")
public class Users {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(nullable = false, updatable = false)
	    private Long iduser;

	    @Column(nullable = false)
	    private String username;

	    @Column(nullable = false)
	    private String email;

	    @Column(nullable = false)
	    private String password;

	    private String telephone;
	    private String nom;
	    private String postnom;
	    private String prenom;
	    private Boolean statut;
	    private Long idecole;
	    private String ecole;
		private Date dateins=new Date();

	    public Date getDateins() {
			return dateins;
		}

		public void setDateins(Date dateins) {
			this.dateins = dateins;
		}

		@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
		private List<Role> roles = new ArrayList<>();
		
		@Transient
		private List<Long> idrole;
	  
	    public Users() {
	    }

	    @Override
		public String toString() {
			return "Users [iduser=" + iduser + ", username=" + username + ", email=" + email + ", password=" + password
					+ ", telephone=" + telephone + ", nom=" + nom + ", postnom=" + postnom + ", prenom=" + prenom
					+ ", statut=" + statut + ", idecole=" + idecole + ", ecole=" + ecole + ", dateins=" + dateins
					+ ", roles=" + roles + ", idrole=" + idrole + "]";
		}

		public Users(String username, String email, String nom, String postnom, String prenom, String telephone, Boolean statut, List<Role> roles, String password,Long idecole,String ecole) {
	        this.username = username;
	        this.email = email;
	        this.nom = nom;
	        this.postnom = postnom;
	        this.prenom = prenom;
	        this.telephone = telephone;
	        this.statut = statut;
	        this.roles = roles;
	        this.idrole = idrole;
	        this.password = password;
	        this.idecole = idecole;
	        this.ecole = ecole;
	    }


	
	    public Long getIduser() {
	        return iduser;
	    }

	    public void setIduser(Long iduser) {
	        this.iduser = iduser;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getTelephone() {
	        return telephone;
	    }

	    public void setTelephone(String telephone) {
	        this.telephone = telephone;
	    }

	    public String getNom() {
	        return nom;
	    }

	    public void setNom(String nom) {
	        this.nom = nom;
	    }

	    public String getPostnom() {
	        return postnom;
	    }

	    public void setPostnom(String postnom) {
	        this.postnom = postnom;
	    }

	    public String getPrenom() {
	        return prenom;
	    }

	    public void setPrenom(String prenom) {
	        this.prenom = prenom;
	    }

	    public Boolean getStatut() {
	        return statut;
	    }

	    public List<Role> getRoles() {
			return roles;
		}

		public void setRoles(List<Role> roles) {
			this.roles = roles;
		}

		public void setStatut(Boolean statut) {
	        this.statut = statut;
	    }

		public Long getIdecole() {
			return idecole;
		}

		public void setIdecole(Long idecole) {
			this.idecole = idecole;
		}

		public List<Long> getIdrole() {
			return idrole;
		}

		public void setIdrole(List<Long> idrole) {
			this.idrole = idrole;
		}

	
		  public Users(List<Long> idrole) {
		        this.idrole = idrole;
		    }

		public String getEcole() {
			return ecole;
		}

		public void setEcole(String ecole) {
			this.ecole = ecole;
		}


		

	   
}

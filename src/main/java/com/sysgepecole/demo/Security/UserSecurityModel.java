package com.sysgepecole.demo.Security;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sysgepecole.demo.Models.Users;

public class UserSecurityModel implements UserDetails {
	
	
	private static final long serialVersionUID = 1L;
	private long iduser;
	private String nom;
	private String postnom;
	private String prenom;
	private String username;
	private String password;
	private String email;
	private String telephone;
	private Boolean statut;
	private Long idecole;
	private String ecole;
	private Collection<? extends GrantedAuthority> authorites;

	

	   public UserSecurityModel(long iduser, String nom, String postnom, String prenom, String username, String password,
               String email, String telephone,Long idecole,String ecole, Boolean statut, Collection<? extends GrantedAuthority> authorities) {
this.iduser = iduser;
this.nom = nom;
this.postnom = postnom;
this.prenom = prenom;
this.username = username;
this.password = password;
this.email = email;
this.telephone = telephone;
this.idecole = idecole;
this.ecole = ecole;
this.statut = statut;
this.authorites = authorities;
}

	
	
	


	   public static UserSecurityModel build(Users user) {
		   
		   List<GrantedAuthority> authorities = user.getRoles().stream()
			        .map(role -> new SimpleGrantedAuthority(role.getRole().trim()))
			        .collect(Collectors.toList());

		    
		    return new UserSecurityModel(
		        user.getIduser(),
		        user.getNom(),
		        user.getPostnom(),
		        user.getPrenom(),
		        user.getUsername(),
		        user.getPassword(),
		        user.getEmail(),
		        user.getTelephone(),
		        user.getIdecole(),
		        user.getEcole(),
		        user.getStatut(),
		        authorities
		    );
		}

	



	public Collection<? extends GrantedAuthority> getAuthorites() {
		return authorites;
	}

	public void setAuthorites(Collection<? extends GrantedAuthority> authorites) {
		this.authorites = authorites;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorites;
	}


	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	public long getIduser() {
		return iduser;
	}

	public void setIduser(long iduser) {
		this.iduser = iduser;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	 @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return statut;
	    }
	    
	@Override
	public String toString() {
		return "UsersSecurityModel [iduser=" + iduser + ", nom=" + nom + ", postnom=" + postnom + ", prenom=" + prenom
				+ ", username=" + username + ", password=" + password + ", email=" + email + ", telephone=" + telephone
				+ ", statut=" + statut + ", authorites=" + authorites + "]";
	}


	public Long getIdecole() {
		return idecole;
	}


	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}


	public String getEcole() {
		return ecole;
	}


	public void setEcole(String ecole) {
		this.ecole = ecole;
	}






	


	
}
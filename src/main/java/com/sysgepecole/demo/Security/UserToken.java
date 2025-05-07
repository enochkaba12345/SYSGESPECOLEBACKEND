package com.sysgepecole.demo.Security;

public class UserToken {

	private long  iduser;
	private String  nom;
	private String  postnom;
	private String  prenom;
	private String  username;
	private String  email;
	private String  telephone;
	private Long  idecole;
	private String  role;
	private String  ecole;
	private String  token;
	private String  typeToken = "Bearer ";
	

	public UserToken(long iduser, String nom, String postnom, String prenom, String username, String email, String telephone,Long idecole,
			 String role,String ecole,String token) {
		super();
		this.iduser = iduser;
		this.nom = nom;
		this.postnom = postnom;
		this.prenom = prenom;
		this.username = username;
		this.email = email;
		this.telephone = telephone;
		this.idecole = idecole;
		this.role = role;
		this.ecole = ecole;
		this.token = token;
		
	}


	public Long getIdecole() {
		return idecole;
	}


	public void setIdecole(Long idecole) {
		this.idecole = idecole;
	}


	public UserToken() {
	
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


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getTypeToken() {
		return typeToken;
	}


	public void setTypeToken(String typeToken) {
		this.typeToken = typeToken;
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

	

	public String getEcole() {
		return ecole;
	}


	public void setEcole(String ecole) {
		this.ecole = ecole;
	}


	@Override
	public String toString() {
		return "UserToken [iduser=" + iduser + ", nom=" + nom + ", postnom=" + postnom + ", prenom=" + prenom
				+ ", username=" + username + ", email=" + email + ", telephone=" + telephone + ", idecole=" + idecole
				+ ", role=" + role + ", ecole=" + ecole + ", token=" + token + ", typeToken=" + typeToken + "]";
	}

	
	
}

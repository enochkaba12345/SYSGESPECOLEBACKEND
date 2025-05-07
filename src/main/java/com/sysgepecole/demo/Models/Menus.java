package com.sysgepecole.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_menus")
public class Menus {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idmenu;

	private String nomurl;
	 private String icon;
	 private String routerLink;
	public long getIdmenu() {
		return idmenu;
	}
	public void setIdmenu(long idmenu) {
		this.idmenu = idmenu;
	}
	public String getNomurl() {
		return nomurl;
	}
	public void setNomurl(String nomurl) {
		this.nomurl = nomurl;
	}


	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getRouterLink() {
		return routerLink;
	}
	public void setRouterLink(String routerLink) {
		this.routerLink = routerLink;
	}
	public Menus() {

	  }

}

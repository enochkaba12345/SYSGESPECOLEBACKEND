package com.sysgepecole.demo.Dto;

import java.util.ArrayList;
import java.util.List;

import com.sysgepecole.demo.Models.Sousmenu;
import com.sysgepecole.demo.Models.Sousmenus;
import com.sysgepecole.demo.Models.Soussmenus;

public class MenuDto {

	private int idrole;
	private String role;

	private Long idmenu;
	private String nomurl;

	private int idmenurole;
	private String urls;
	private String icon;
	private Boolean disabled;
	private Boolean prevent;
	private Boolean visible;
	private List<String> roles;
	private List<Sousmenu> sousmenuItems = new ArrayList<>();
	private List<Soussmenus> soussmenusItems = new ArrayList<>();
	private List<Sousmenus> sousmenusItems = new ArrayList<>();
	


	public int getIdrole() {
		return idrole;
	}

	public void setIdrole(int i) {
		this.idrole = i;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getIdmenu() {
		return idmenu;
	}

	public void setIdmenu(Long idmenu) {
		this.idmenu = idmenu;
	}

	public String getNomurl() {
		return nomurl;
	}

	public void setNomurl(String nomurl) {
		this.nomurl = nomurl;
	}

	public int getIdmenurole() {
		return idmenurole;
	}

	public void setIdmenurole(int i) {
		this.idmenurole = i;
	}
	
	

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getPrevent() {
		return prevent;
	}

	public void setPrevent(Boolean prevent) {
		this.prevent = prevent;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	



	public List<Sousmenu> getSousmenuItems() {
		return sousmenuItems;
	}

	public void setSousmenuItems(List<Sousmenu> sousmenuItems) {
		this.sousmenuItems = sousmenuItems;
	}

	public List<Soussmenus> getSoussmenusItems() {
		return soussmenusItems;
	}

	public void setSoussmenusItems(List<Soussmenus> soussmenusItems) {
		this.soussmenusItems = soussmenusItems;
	}
	

	

	public List<Sousmenus> getSousmenusItems() {
		return sousmenusItems;
	}

	public void setSousmenusItems(List<Sousmenus> sousmenusItems) {
		this.sousmenusItems = sousmenusItems;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public MenuDto() {
		super();
	}



	@Override
	public String toString() {
		return "MenuDto [idrole=" + idrole + ", role=" + role + ", idmenu=" + idmenu + ", nomurl=" + nomurl
				+ ", idmenurole=" + idmenurole + ", urls=" + urls + ", icon=" + icon + ", disabled=" + disabled
				+ ", prevent=" + prevent + ", visible=" + visible + ", roles=" + roles + ", sousmenuItems="
				+ sousmenuItems + ", soussmenusItems=" + soussmenusItems + ", sousmenusItems=" + sousmenusItems + "]";
	}



	
	
	
	

}

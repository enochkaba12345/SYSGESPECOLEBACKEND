package com.sysgepecole.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_menusroles")
public class Menusroles {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idmenurole;
	private long idmenu;
	private String sousurl;
    private String url;
    private long idrole;
    private String icon;
    private Boolean disabled;
    private Boolean prevent;
    private Boolean visible;
    
	
	public Menusroles() {

	  }

	public long getIdmenurole() {
		return idmenurole;
	}

	public void setIdmenurole(long idmenurole) {
		this.idmenurole = idmenurole;
	}

	public long getIdmenu() {
		return idmenu;
	}

	public void setIdmenu(long idmenu) {
		this.idmenu = idmenu;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getIdrole() {
		return idrole;
	}

	public void setIdrole(long idrole) {
		this.idrole = idrole;
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

	public String getSousurl() {
		return sousurl;
	}

	public void setSousurl(String sousurl) {
		this.sousurl = sousurl;
	}
	

}

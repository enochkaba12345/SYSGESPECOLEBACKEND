package com.sysgepecole.demo.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Tab_sousmenu")
public class Sousmenu {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idsousmenu;
	private String sousurl;
	private String url;
	private long idmenurole;
    private String icon;
    private Boolean disabled;
    private Boolean prevent;
    private Boolean visibled;
	public long getIdsousmenu() {
		return idsousmenu;
	}
	public void setIdsousmenu(long idsousmenu) {
		this.idsousmenu = idsousmenu;
	}
	public String getSousurl() {
		return sousurl;
	}
	public void setSousurl(String sousurl) {
		this.sousurl = sousurl;
	}

	public long getIdmenurole() {
		return idmenurole;
	}
	public void setIdmenurole(long idmenurole) {
		this.idmenurole = idmenurole;
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
	public Boolean getVisibled() {
		return visibled;
	}
	public void setVisibled(Boolean visibled) {
		this.visibled = visibled;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	


	
	
}

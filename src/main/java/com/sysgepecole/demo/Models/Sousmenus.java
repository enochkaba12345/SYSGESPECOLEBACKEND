package com.sysgepecole.demo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "Tab_sousmenues")
public class Sousmenus {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idsousmenues;
	private String sousurl;
	private String url;
	private long idsousmenus;
    private String icon;
    private Boolean disabled;
    private Boolean prevent;
    private Boolean visibled;
	public long getIdsousmenues() {
		return idsousmenues;
	}
	public void setIdsousmenues(long idsousmenues) {
		this.idsousmenues = idsousmenues;
	}
	public String getSousurl() {
		return sousurl;
	}
	public void setSousurl(String sousurl) {
		this.sousurl = sousurl;
	}	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getIdsousmenus() {
		return idsousmenus;
	}
	public void setIdsousmenus(long idsousmenus) {
		this.idsousmenus = idsousmenus;
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
	
    
    
}

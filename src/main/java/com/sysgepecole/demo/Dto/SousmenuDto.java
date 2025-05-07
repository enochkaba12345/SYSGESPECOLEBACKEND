package com.sysgepecole.demo.Dto;

import java.util.ArrayList;
import java.util.List;

import com.sysgepecole.demo.Models.Soussmenus;

public class SousmenuDto {
	
	private long idsousmenus;
	private String sousurls;
	private long idsousmenu;
	private String Link;
	private String icon;
	private Boolean disabled;
	private Boolean prevent;
	private Boolean visible;
	private List<Soussmenus> SousmenuItems = new ArrayList<>();
	public long getIdsousmenus() {
		return idsousmenus;
	}
	public void setIdsousmenus(long idsousmenus) {
		this.idsousmenus = idsousmenus;
	}
	public String getSousurls() {
		return sousurls;
	}
	public void setSousurls(String sousurls) {
		this.sousurls = sousurls;
	}
	public long getIdsousmenu() {
		return idsousmenu;
	}
	public void setIdsousmenu(long idsousmenu) {
		this.idsousmenu = idsousmenu;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
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
	public List<Soussmenus> getSousmenuItems() {
		return SousmenuItems;
	}
	public void setSousmenuItems(List<Soussmenus> sousmenuItems) {
		SousmenuItems = sousmenuItems;
	}

	
	
	

}

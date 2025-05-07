package com.sysgepecole.demo.Dto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.sysgepecole.demo.Models.Sousmenu;
import com.sysgepecole.demo.Models.Sousmenus;
import com.sysgepecole.demo.Models.Soussmenus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuResultSetExtractor implements ResultSetExtractor<List<MenuDto>>{
	
	@Override
	public List<MenuDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
	    Map<Long, MenuDto> menuMap = new LinkedHashMap<>();

	    while (rs.next()) {
	        Long menuId = rs.getLong("idmenu");
	        MenuDto menu = menuMap.get(menuId);

	       
	        if (menu == null) {
	            menu = new MenuDto();
	            menu.setIdmenu(menuId);
	            menu.setNomurl(rs.getString("nomurl"));
	            menu.setUrls(rs.getString("url"));
	            menu.setIdmenurole(rs.getInt("idmenurole"));
	            menu.setIdrole(rs.getInt("idrole"));
	            menu.setRole(rs.getString("role"));
	            menu.setIcon(rs.getString("icon"));
	            menu.setDisabled(rs.getBoolean("disabled"));
	            menu.setPrevent(rs.getBoolean("prevent"));
	            menu.setVisible(rs.getBoolean("visible"));
	            menuMap.put(menuId, menu);
	        }

	      
	        Long sousmenuId = rs.getLong("idsousmenu");
	        if (!rs.wasNull()) {
	            Sousmenu sousmenu = new Sousmenu();
	            sousmenu.setIdsousmenu(sousmenuId);
	            sousmenu.setSousurl(rs.getString("sousurl"));
	            sousmenu.setUrl(rs.getString("sousmenu_url"));
	            sousmenu.setIcon(rs.getString("icon"));
	            sousmenu.setIdmenurole(rs.getLong("idmenurole"));
	            sousmenu.setDisabled(rs.getBoolean("disabled"));
	            sousmenu.setPrevent(rs.getBoolean("prevent"));
	            sousmenu.setVisibled(rs.getBoolean("visible"));
	            menu.getSousmenuItems().add(sousmenu);
	        }

	       
	        Long sousmenusId = rs.getLong("idsousmenus");
	        if (!rs.wasNull()) {
	            Soussmenus soussmenus = new Soussmenus();
	            soussmenus.setIdsousmenus(sousmenusId);
	            soussmenus.setSousurl(rs.getString("sousurl"));
	            soussmenus.setIcon(rs.getString("icon"));
	            soussmenus.setUrl(rs.getString("soussmenus_url"));
	            soussmenus.setIdsousmenu(rs.getLong("idsousmenu"));
	            soussmenus.setDisabled(rs.getBoolean("disabled"));
	            soussmenus.setPrevent(rs.getBoolean("prevent"));
	            soussmenus.setVisibled(rs.getBoolean("visible"));
	            menu.getSoussmenusItems().add(soussmenus);
	        }
	        
	        Long sousmenuesId = rs.getLong("idsousmenues");
	        if (!rs.wasNull()) {
	        	Sousmenus sousmenus = new Sousmenus();
	        	sousmenus.setIdsousmenues(sousmenuesId);
	        	sousmenus.setSousurl(rs.getString("sousurl"));
	        	sousmenus.setIcon(rs.getString("icon"));
	        	sousmenus.setUrl(rs.getString("soussmenues_url"));
	        	sousmenus.setIdsousmenus(rs.getLong("idsousmenus"));
	        	sousmenus.setDisabled(rs.getBoolean("disabled"));
	        	sousmenus.setPrevent(rs.getBoolean("prevent"));
	        	sousmenus.setVisibled(rs.getBoolean("visible"));
	            menu.getSousmenusItems().add(sousmenus);
	        }
	    }

	    return new ArrayList<>(menuMap.values());
	}

}

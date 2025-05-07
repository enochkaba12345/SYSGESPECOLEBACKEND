package com.sysgepecole.demo.ServiceImpl;


import java.sql.Types;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


import com.sysgepecole.demo.Dto.MenuDto;

import com.sysgepecole.demo.Repository.MenusRepository;
import com.sysgepecole.demo.Service.MenusService;
import com.sysgepecole.demo.Dto.MenuResultSetExtractor;




@Service
public class MenusServiceImpl implements MenusService{

	
	@Autowired
	public MenusRepository menusrepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	    public List<MenuDto> getAllMenus(String role) {
	        String query = " SELECT r.idrole, r.role,"
	        			 + " m.nomurl, m.idmenu, m.icon,"
	        		     + " rm.idmenurole,  rm.icon, rm.disabled, rm.prevent, rm.visible, rm.url, "
	        		     + " s.idsousmenu, s.sousurl, s.icon, s.disabled, s.prevent, s.visibled, s.url AS sousmenu_url, "
	        		     + " sm.idsousmenus, sm.url AS soussmenus_url, sm.sousurl, sm.disabled, sm.prevent, sm.visibled "
	        		     + " idsousmenues"
	                     + " FROM tab_menus m "
	                     + " JOIN tab_menusroles rm ON m.idmenu = rm.idmenu "
	                     + " JOIN tab_role r ON rm.idrole = r.idrole "
	                     + " LEFT JOIN tab_sousmenu s ON s.idmenurole = rm.idmenurole " 
	                     + " LEFT JOIN tab_sousmenus sm ON sm.idsousmenu = s.idsousmenu " 
	                     + " LEFT JOIN Tab_sousmenues srm ON srm.idsousmenus = sm.idsousmenus " 
	                     + " WHERE r.role = :role "
	                     + " ORDER BY m.nomurl DESC ";

	        MapSqlParameterSource parameters = new MapSqlParameterSource();
	        parameters.addValue("role", role.trim(), Types.VARCHAR);
	        return namedParameterJdbcTemplate.query(query, parameters, new MenuResultSetExtractor());
	    }
	
	
	 
}

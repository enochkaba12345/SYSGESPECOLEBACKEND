package com.sysgepecole.demo.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sysgepecole.demo.Dto.EleveModelDto;
import com.sysgepecole.demo.Dto.RoleDto;
import com.sysgepecole.demo.Repository.RoleRepository;
import com.sysgepecole.demo.Service.RoleService;



@Service
public class RoleServiceImpl implements RoleService{

	
	@Autowired
	public RoleRepository profilrepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	public List<RoleDto> getAllRoles() {
	    String query = "SELECT idrole, role FROM tab_Role ORDER BY idrole ASC";
	    return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(RoleDto.class));
	}
	
	public ResponseEntity<?> getAllRole() {
		  List<RoleDto> collections = getAllRoles();

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune Role trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	
	public List<RoleDto> getRoles(String role) {
	    String query = "SELECT idrole, role FROM tab_Role"
	    		  + " WHERE role = :role "
	    		+ " ORDER BY idrole ASC";
	 	 MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("role", role);
		    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(RoleDto.class));
	}
	
	public ResponseEntity<?> getRole(String role) {
		  List<RoleDto> collections = getRoles(role);

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune Role trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	
	public List<String> getUserRoles() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getAuthorities().stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList());
        }

        return List.of();
    }
	
	 public ResponseEntity<?> getRolesForCurrentUser() {
	        List<String> userRoles = getUserRoles();
	        List<RoleDto> allRoles = getAllRoles();

	        List<RoleDto> filteredRoles;

	        if (userRoles.contains("ADMIN")) {
	            filteredRoles = allRoles;
	        } else if (userRoles.contains("DIRECTEUR")) {
	            filteredRoles = allRoles.stream()
	                    .filter(role -> !role.getRole().equals("ADMIN"))
	                    .collect(Collectors.toList());
	        } else {
	         
	            filteredRoles = List.of();
	        }

	        if (filteredRoles.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun rôle trouvé.");
	        } else {
	            return ResponseEntity.ok(filteredRoles);
	        }
	    }
}

package com.sysgepecole.demo.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sysgepecole.demo.Dto.UserModelDto;
import com.sysgepecole.demo.Models.Role;
import com.sysgepecole.demo.Models.Users;
import com.sysgepecole.demo.Repository.RoleRepository;
import com.sysgepecole.demo.Repository.UsersRepository;
import com.sysgepecole.demo.Service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository usesrepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public ResponseEntity<Users> updateUserIdentique(Long iduser, Users user) {
		Optional<Users> UserData = usesrepository.findById(iduser);
		if (UserData.isPresent()) {
			Users users = UserData.get();
			users.setNom(user.getNom());
			users.setPostnom(user.getPostnom());
			users.setPrenom(user.getPrenom());
			users.setIduser(user.getIduser());
			users.setTelephone(user.getTelephone());
			users.setEmail(user.getEmail());
			users.setRoles(user.getRoles());
			users.setIdecole(user.getIdecole());
			return new ResponseEntity<>(usesrepository.save(user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity<Users> updateUser(Long iduser, Users user) {
	    Optional<Users> existingUserData = usesrepository.findById(iduser);

	    if (existingUserData.isPresent()) {
	        Users existingUser = existingUserData.get();

	      
	        existingUser.setNom(user.getNom());
	        existingUser.setPostnom(user.getPostnom());
	        existingUser.setPrenom(user.getPrenom());
	        existingUser.setTelephone(user.getTelephone());
	        existingUser.setEmail(user.getEmail());
	        existingUser.setIdecole(user.getIdecole());
	        existingUser.setStatut(user.getStatut());

	        
	        if (user.getIdrole() != null && !user.getIdrole().isEmpty()) {
	            List<Role> roles = new ArrayList<>();
	            user.getIdrole().forEach(idrole -> {
	                if (idrole == null) {
	                    throw new RuntimeException("Role ID must not be null");
	                }
	                Role role = roleRepository.findById(idrole)
	                    .orElseThrow(() -> new RuntimeException("Role not found with ID: " + idrole));
	                roles.add(role);
	            });
	            existingUser.setRoles(roles);
	        }

	        // Save updated user
	        Users updatedUser = usesrepository.save(existingUser);
	        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	 
	    public List<UserModelDto> CollecteUsers(List<String> roles, Long idecole) {
	        String query = "SELECT b.iduser, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, "
	                     + "b.email, b.telephone, b.statut, UPPER(b.username) AS username, b.password, e.idrole, "
	                     + "UPPER(e.role) AS role, b.idecole "
	                     + "FROM tab_User b "
	                     + "JOIN tab_User_roles c ON b.iduser = c.users_iduser "
	                     + "JOIN tab_Role e ON c.roles_idrole = e.idrole ";

	        Map<String, Object> params = new HashMap<>();
	        boolean hasRoles = roles != null && !roles.isEmpty();
	        boolean hasIdecole = idecole != null;

	        if (hasRoles) {
	            query += " WHERE UPPER(e.role) IN (:roles)";
	            params.put("roles", roles.stream().map(String::toUpperCase).collect(Collectors.toList()));
	        }

	        if (hasIdecole) {
	            query += hasRoles ? " AND b.idecole = :idecole" : " WHERE b.idecole = :idecole";
	            params.put("idecole", idecole);
	        }

	        query += " ORDER BY b.iduser ASC";

	        return namedParameterJdbcTemplate.query(query, params, new BeanPropertyRowMapper<>(UserModelDto.class));
	    }

	    
	    public ResponseEntity<?> collectUsers(String userRole, Long idecole) {
	        List<UserModelDto> collections;

	        if ("ADMIN".equalsIgnoreCase(userRole)) {
	            collections = CollecteUsers(null, null); 
	        } else if ("DIRECTEUR".equalsIgnoreCase(userRole)) {
	            List<String> allowedRoles = Arrays.asList("DIRECTEUR", "ENCODEUR", "CAISSE");
	            collections = CollecteUsers(allowedRoles, idecole);
	        } else {
	            return ResponseEntity.ok(Collections.emptyList());
	        }

	        if (collections.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun utilisateur trouvé pour ces paramètres.");
	        }
	        return ResponseEntity.ok(collections);
	    }



	public List<UserModelDto> searchUsers(String username) {
		String query = "SELECT b.iduser, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom,"
				+ "	b.email,b.telephone,b.statut,UPPER(b.username) AS username,b.password, e.idrole, UPPER(e.role) AS role, b.idecole" 
				+ "	FROM tab_User b"
				+ "	JOIN tab_User_roles c ON b.iduser = c.users_iduser"
				+ "	JOIN tab_Role e ON c.roles_idrole = e.idrole" 
				+ " WHERE LOWER(b.username) LIKE :username"
				+ "	 ORDER BY b.iduser ASC";
		MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("username",
				"%" + username.toLowerCase() + "%");
		return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(UserModelDto.class));
	}

	public ResponseEntity<?> searchUser(String username) {
		List<UserModelDto> collections = searchUsers(username);

		if (collections.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun Utilisateur trouvé pour ce nom.");
		} else {
			return ResponseEntity.ok(collections);
		}
	}
	
	

	@Override
	public String updatePassword(Long iduser) {
		System.out.println(iduser);
	    if (iduser == null) {
	        throw new IllegalArgumentException("ID utilisateur manquant.");
	    }

	    Optional<Users> optionalUser = usesrepository.findById(iduser);
	    if (!optionalUser.isPresent()) {
	        System.out.println("Utilisateur introuvable (kaba3)");
	        throw new RuntimeException("Utilisateur introuvable.");
	    }

	    Users user = optionalUser.get();

	    String defaultPassword = "Sysgespecole@2025";
	    String encodedPassword = passwordEncoder.encode(defaultPassword);
	    user.setPassword(encodedPassword);
	    System.out.println("Mot de passe réinitialisé (kaba)");

	    usesrepository.save(user);
	    return "Mot de passe initialisé et statut activé avec succès.";
	}


	  
	    @Override
	    public String updateStatus(Long iduser, Boolean statut) {
	        Optional<Users> userOptional = usesrepository.findById(iduser);
	        if (!userOptional.isPresent()) {
	            throw new RuntimeException("Utilisateur introuvable.");
	        }

	        Users user = userOptional.get();
	        user.setStatut(statut); 
	        usesrepository.save(user); 
	        return "Statut changé avec succès.";
	    }

	    @Override
	    public String updatesPassword(Long iduser, String password) {
	        Optional<Users> userOptional = usesrepository.findById(iduser);
	        if (!userOptional.isPresent()){
	            throw new RuntimeException("Utilisateur introuvable.");
	        }
	        Users user = userOptional.get();
	       
	        String encodedPassword = passwordEncoder.encode(password);
	        user.setPassword(encodedPassword);
	      
	        
	        usesrepository.save(user);
	        return "Mot de passe changé avec succès.";
	    }

		
		

}

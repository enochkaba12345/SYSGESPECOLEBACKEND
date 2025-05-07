package com.sysgepecole.demo.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.sysgepecole.demo.Dto.LoginRequest;
import com.sysgepecole.demo.Dto.SignupRequest;
import com.sysgepecole.demo.Models.Ecole;
import com.sysgepecole.demo.Models.Paiement;
import com.sysgepecole.demo.Models.Role;
import com.sysgepecole.demo.Models.Users;
import com.sysgepecole.demo.Repository.EcoleRepository;
import com.sysgepecole.demo.Repository.RoleRepository;
import com.sysgepecole.demo.Repository.UsersRepository;
import com.sysgepecole.demo.Security.JwtProvider;
import com.sysgepecole.demo.Security.UserSecurityModel;
import com.sysgepecole.demo.Security.UserToken;
import com.sysgepecole.demo.Service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
    private PasswordEncoder  passwordEncoder;

	@Autowired
	JwtProvider jwtService;
	
	@Autowired
	private AuthService authService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	EcoleRepository ecoleRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/signin")
	public ResponseEntity<?> signin(@Validated @RequestBody LoginRequest model) {

		try {
			if (model.getPassword() == null || model.getPassword().isEmpty()) {
				return new ResponseEntity<String>("Fail -> Password cannot be empty!", HttpStatus.BAD_REQUEST);
			}

			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));
			
			   UserSecurityModel userSecurityModel = (UserSecurityModel) authentication.getPrincipal();
			
				
				SecurityContextHolder.getContext().setAuthentication(authentication);

				String jwt = jwtService.generateJwtToken(authentication);
				
				 Optional<String> role = userSecurityModel.getAuthorities().stream().map(item -> item.getAuthority()).findFirst();
				 
		
				 Long idEcole = userSecurityModel.getIdecole();
				 Optional<Ecole> ecole = (idEcole != null) ? ecoleRepository.findById(idEcole) : Optional.empty(); 
				 
				   return ResponseEntity.ok(new UserToken(
			        		userSecurityModel.getIduser(),
			        		userSecurityModel.getNom(),
			        		userSecurityModel.getPostnom(),
			        		userSecurityModel.getPrenom(),
			        		userSecurityModel.getUsername(),
			        		userSecurityModel.getEmail(),
			        		userSecurityModel.getTelephone(),
			        		idEcole != null ? idEcole : null,
			        		role.get().toString(),
			        		ecole.map(Ecole::getEcole).orElse("École inconnue"),   
			                jwt
			                
			        ));
     

		} catch (Exception e) {
			return new ResponseEntity<String>("Fail -> Connection echouée!", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Validated @RequestBody SignupRequest signUpRequest) {
	    if (usersRepository.existsByUsername(signUpRequest.getUsername())) {
	        return new ResponseEntity<>("Fail -> Username is already taken!", HttpStatus.BAD_REQUEST);
	    }

	    if (usersRepository.existsByEmail(signUpRequest.getEmail())) {
	        return new ResponseEntity<>("Fail -> Email is already taken!", HttpStatus.BAD_REQUEST);
	    }

	    String defaultPassword = "Sysgespecole@2025";
	    String password = signUpRequest.getPassword() != null && !signUpRequest.getPassword().isEmpty()
	            ? signUpRequest.getPassword()
	            : defaultPassword;

	    if (password.isEmpty()) {
	        return new ResponseEntity<>("Fail -> Password cannot be empty!", HttpStatus.BAD_REQUEST);
	    }

	    Users user = new Users();
	    user.setUsername(signUpRequest.getUsername());
	    user.setEmail(signUpRequest.getEmail());
	    user.setTelephone(signUpRequest.getTelephone());
	    user.setNom(signUpRequest.getNom());
	    user.setPostnom(signUpRequest.getPostnom());
	    user.setPrenom(signUpRequest.getPrenom());
	    user.setStatut(signUpRequest.getStatut());
	    user.setIduser(signUpRequest.getIduser());
	    user.setIdecole(signUpRequest.getIdecole());

	    List<Role> roles = new ArrayList<>();
	    signUpRequest.getIdrole().forEach(idrole -> {
	        Role role = roleRepository.findById(idrole)
	                .orElseThrow(() -> new RuntimeException("Role not found."));
	        roles.add(role);
	    });
	    user.setRoles(roles);
	    

	    String encodedPassword = passwordEncoder.encode(password);
	    user.setPassword(encodedPassword);


	    Users savedUser = usersRepository.save(user);
	    if (savedUser != null) {
	        return ResponseEntity.ok().body(savedUser);
	    } else {
	        return new ResponseEntity<>("Utilisateur enregistrer echouée!", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
	    String authHeader = request.getHeader("Authorization");
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	         String token = authHeader.substring(7);
	        return ResponseEntity.ok("Déconnexion réussie.");
	    }
	    HttpSession session = request.getSession(false); 
        if (session != null) {
            session.invalidate(); 
        }
	    return ResponseEntity.badRequest().body("Token manquant.");
	}

	
	 
	
	    
	
}

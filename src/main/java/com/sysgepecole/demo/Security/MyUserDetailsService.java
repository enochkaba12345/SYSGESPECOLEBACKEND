package com.sysgepecole.demo.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sysgepecole.demo.Models.Users;
import com.sysgepecole.demo.Repository.UsersRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UsersRepository userRepository;
	
	public MyUserDetailsService() {
		super();
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    Users user = userRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
	    
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    user.getRoles().forEach(role -> {
	        GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole().trim());
	        System.out.println("Role for user: " + role.getRole());
	        authorities.add(authority);
	    });


	  
	    return UserSecurityModel.build(user);
	}

	 
	
	 public UserSecurityModel userContext() {
		 UserSecurityModel userContext = null;

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {

				UserSecurityModel user = (UserSecurityModel) auth.getPrincipal();
				
				Optional<String> role = user.getAuthorities().stream().map(item -> item.getAuthority()).findFirst();
				
				userContext = new UserSecurityModel(user.getIduser(),user.getNom(),user.getPostnom(),user.getPrenom(),user.getUsername(),user.getPassword(),user.getEmail(),user.getTelephone(),user.getIdecole(),user.getEcole(),user.getStatut(), user.getAuthorities());
			}

			return userContext;
		}

}

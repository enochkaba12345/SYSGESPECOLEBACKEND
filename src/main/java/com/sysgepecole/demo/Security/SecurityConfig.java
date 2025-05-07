package com.sysgepecole.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public JwtAuthTokenFilter authenjwtAuthTokenFilter() {
		return new JwtAuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(myUserDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public MyUserDetailsService myUserDetailsService() {
		return new MyUserDetailsService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity.cors().and().csrf().disable()
	        .authorizeHttpRequests()
	            .requestMatchers("/api/auth/**", "/api/whatsapp/**", "/uploads/**", "/logos/**").permitAll() 
	            .requestMatchers("/api/paiement/**", "/api/annee/**", "/api/ecole/**", "/api/classe/**", 
	                     "/api/tranche/**", "/api/categorie/**", "/api/province/**", "/api/commune/**")
	    .hasAnyAuthority("CAISSE", "ENCODEUR", "DIRECTEUR", "ADMIN")
	    .requestMatchers("/api/eleve/**", "/api/photo/**", "/api/logos/**").hasAnyAuthority("CAISSE","ENCODEUR", "DIRECTEUR", "ADMIN")
	    .requestMatchers("/api/frais/**", "/api/intermedaireannee/**", "/api/intermedaireclasse/**", 
	                     "/api/niveau/**", "/api/role/**", "/api/user/**")
	    .hasAnyAuthority("DIRECTEUR", "ADMIN")
	            .anyRequest().authenticated()
	        .and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    httpSecurity.addFilterBefore(authenjwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	    return httpSecurity.build();
	}

}

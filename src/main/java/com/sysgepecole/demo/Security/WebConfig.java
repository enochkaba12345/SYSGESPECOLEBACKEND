package com.sysgepecole.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
			   .allowedOriginPatterns("https://sysgespecoles.netlify.app", "http://localhost:4200")
                        .allowedMethods("HEAD","OPTION","GET","POST","PUT","PATCH","DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

	 @Bean
        public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "https://sysgespecoles.netlify.app"
        ));
        config.setAllowedHeaders(Arrays.asList("*"));

        // Autoriser toutes les méthodes (GET, POST, PUT, DELETE...)
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:F:/uploads/");  

        registry.addResourceHandler("/logos/**")
        .addResourceLocations("file:F:/logos/");
	}
	
	

}

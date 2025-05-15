package com.sysgepecole.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = "com.sysgepecole.demo")
public class SysgespecoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysgespecoleApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	return new WebMvcConfigurer() {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/")
			.allowedMethods("HEAD","OPTION","GET","POST","PUT","PATCH","DELETE")
			.allowCredentials(false).maxAge(3600);
		}
	};
}

}

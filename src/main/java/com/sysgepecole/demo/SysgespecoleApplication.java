package com.sysgepecole.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.sysgepecole.demo", "com.sysgepecole.demo.Security"})
public class SysgespecoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysgespecoleApplication.class, args);
	}
	
	

}

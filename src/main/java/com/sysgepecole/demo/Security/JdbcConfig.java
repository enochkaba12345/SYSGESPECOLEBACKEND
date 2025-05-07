package com.sysgepecole.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {
	
	 @Bean
	    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	        return new JdbcTemplate(dataSource);
	    }

	    @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("org.postgresql.Driver"); // Use the PostgreSQL driver class
	        dataSource.setUrl("jdbc:postgresql://localhost:5432/SYSGESPECOLE"); // Replace with your PostgreSQL database URL
	        dataSource.setUsername("postgres"); // Replace with your PostgreSQL username
	        dataSource.setPassword("12345"); // Replace with your PostgreSQL password
	        return dataSource;
	    }

}

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
	        dataSource.setUrl("jdbc:postgresql://dpg-d0esmc95pdvs73b21u4g-a.oregon-postgres.render.com:5432/bddsysgespecole"); // Replace with your PostgreSQL database URL
	        dataSource.setUsername("bddsysgespecole_user"); // Replace with your PostgreSQL username
	        dataSource.setPassword("L84pxZ3P6P0BCUekj5ffsfoQpZ51SWM5"); // Replace with your PostgreSQL password
	        return dataSource;
	    }

}

package com.sysgepecole.demo.ServiceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


import com.sysgepecole.demo.Models.Province;
import com.sysgepecole.demo.Repository.ProvinceRepository;
import com.sysgepecole.demo.Service.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService{

	@Autowired
	public ProvinceRepository provincerepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	public List<Province> getAllProvinces() {
	    String query = "SELECT idprovince, province FROM tab_Province ORDER BY province ASC";
	    return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(Province.class));
	}
	
	public ResponseEntity<?> getAllProvince() {
		  List<Province> collections = getAllProvinces();

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune province trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}


	

}

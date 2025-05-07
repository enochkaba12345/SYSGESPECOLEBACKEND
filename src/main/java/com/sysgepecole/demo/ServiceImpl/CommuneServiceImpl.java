package com.sysgepecole.demo.ServiceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sysgepecole.demo.Models.Commune;
import com.sysgepecole.demo.Repository.CommuneRepository;
import com.sysgepecole.demo.Service.CommuneService;

@Service
public class CommuneServiceImpl implements CommuneService{
	
	@Autowired
	private CommuneRepository communerepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public List<Commune> getAllCommune() {
		return communerepository.findAll();
	}

	public List<Commune> getcommuneByIdprovinces(long idprovince) {
	    String query = "SELECT a.idprovince, a.province, b.idcommune , b.commune"
	            + " FROM tab_Province a "
	            + " JOIN tab_Commune b ON a.idprovince = b.idprovince "
	            + " where a.idprovince = :idprovince"
	            + " ORDER BY b.commune asc";
	    MapSqlParameterSource parameters = new MapSqlParameterSource() .addValue("idprovince", idprovince);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(Commune.class));
	}
	
	public ResponseEntity<?> getcommuneByIdprovince(Long idprovince) {
		 List<Commune> collections = getcommuneByIdprovinces(idprovince);

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun commune trouvé pour ce nom.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}

	

}

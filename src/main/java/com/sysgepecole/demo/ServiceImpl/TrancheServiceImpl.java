package com.sysgepecole.demo.ServiceImpl;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;



import com.sysgepecole.demo.Models.Tranche;
import com.sysgepecole.demo.Repository.FraisRepository;
import com.sysgepecole.demo.Repository.TrancheRepository;
import com.sysgepecole.demo.Service.TrancheService;

@Service
public class TrancheServiceImpl implements TrancheService{

	@Autowired
	public TrancheRepository trancherepository;
	
	@Autowired
	public FraisRepository fraisRepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<Tranche> getAllTranches() {
	    String query = "SELECT idtranche, tranche FROM tab_Tranche ORDER BY tranche ASC";
	    return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(Tranche.class));
	}
	
	public ResponseEntity<?> getAllTranche() {
		  List<Tranche> collections = getAllTranches();

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune tranche trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}

	public ResponseEntity<?> getAllProvince() {
		return null;
	}

	

	


}

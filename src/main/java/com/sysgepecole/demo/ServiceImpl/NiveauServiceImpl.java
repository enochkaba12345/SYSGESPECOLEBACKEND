package com.sysgepecole.demo.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sysgepecole.demo.Dto.ClasseModelDto;
import com.sysgepecole.demo.Models.Niveau;
import com.sysgepecole.demo.Repository.NiveauRepository;
import com.sysgepecole.demo.Service.NiveauService;

@Service
public class NiveauServiceImpl implements NiveauService{

	@Autowired
	public NiveauRepository niveaurepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	public List<Niveau> getAllNiveaux() {
	    String query = "SELECT idniveau, niveau FROM tab_Niveau ORDER BY idniveau ASC";
	    return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(Niveau.class));
	}
	
	public ResponseEntity<?> getAllNiveau() {
		  List<Niveau> collections = getAllNiveaux();

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune niveau trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	
	public List<ClasseModelDto> CollecteNiveaux(long idecole) {
	    String query = "SELECT  a.idecole, UPPER(a.ecole) AS ecole,cl.idclasse, UPPER(cl.classe) AS classe, "
	    		+ "    ic.idintermedaireclasse "
	    		+ "FROM tab_Ecole a "
	    		+ "JOIN tab_Intermedaireclasse ic ON ic.idecole = a.idecole "
	    		+ "JOIN tab_Classe cl ON cl.idclasse = ic.idclasse "
	    		+ "JOIN tab_niveau nv ON nv.idniveau = cl.idniveau "
	    		+ "WHERE nv.idniveau = :idniveau AND a.idecole = :idecole "
	    		+ "ORDER BY cl.idclasse DESC";
	 	     MapSqlParameterSource parameters = new MapSqlParameterSource()
	 	    		.addValue("idecole", idecole);
		    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(ClasseModelDto.class));
	}
	
	public ResponseEntity<?> CollecteNiveau(long idecole) {
		  List<ClasseModelDto> collections = CollecteNiveaux(idecole);

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune niveau trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	
	


}

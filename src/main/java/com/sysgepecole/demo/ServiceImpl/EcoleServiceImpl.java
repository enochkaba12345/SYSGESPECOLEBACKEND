package com.sysgepecole.demo.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;



import com.sysgepecole.demo.Dto.EcoleDto;
import com.sysgepecole.demo.Dto.PaiementDto;
import com.sysgepecole.demo.Models.Ecole;
import com.sysgepecole.demo.Repository.EcoleRepository;
import com.sysgepecole.demo.Service.EcoleService;

@Service
public class EcoleServiceImpl implements EcoleService{
	
	
	@Autowired
	private EcoleRepository ecolerepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Override
	public Optional<Ecole> findEcoleByEcole(String ecole) {
		return ecolerepository.findByEcole(ecole); 
	}

	@Override
	public Ecole createEcoles(Ecole ecole) {
		Optional<Ecole> existingEntity = findEcoleByEcole(ecole.getEcole());
		if (existingEntity.isPresent()) {
		
			} else {
				
				return ecolerepository.save(ecole); 
				}
		return ecole;
	}
	
	@Override
	public ResponseEntity<Ecole> updateEcoles(Long idecole, Ecole ecole) {
		Optional<Ecole> EcoleData = ecolerepository.findById(idecole);

		if (EcoleData.isPresent()) {
			Ecole ecoles = EcoleData.get();
			ecoles.setIdecole(ecole.getIdecole());
			ecoles.setEcole(ecole.getEcole());
			ecoles.setIdprovince(ecole.getIdprovince());
			ecoles.setIdcommune(ecole.getIdcommune());
			ecoles.setIduser(ecole.getIduser());
			return new ResponseEntity<>(ecolerepository.save(ecole), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	



	@Override
	public ResponseEntity<Map<String, Boolean>> delete(Long idecole) {
		Optional<Ecole> ecole = ecolerepository.findById(idecole);
		if (ecole.isPresent()) {
			ecolerepository.deleteById(idecole);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	  public List<EcoleDto> getEcoles(long idecole) {
	        String query = "SELECT a.idecole,UPPER(a.ecole) AS ecole,"
	        		+ "	c.idintermedaireannee,c.idecole,c.idannee "
	        		+ "	FROM tab_Ecole a"
	        		+ "	JOIN tab_Intermedaireannee c ON c.idecole = a.idecole"
	        		+ "	JOIN tab_Annee e ON e.idannee = c.idannee"
	        		+ "	WHERE a.idecole = :idecole "
	        		+ "	ORDER BY ecole DESC";
	        
	        MapSqlParameterSource parameters = new MapSqlParameterSource()
	                .addValue("idecole", idecole);
	            return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EcoleDto.class));
	  
	    }


	    public ResponseEntity<?> getEcole(long idecole) {
	        List<EcoleDto> collections = getEcoles(idecole);
	        
	        if (collections.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
	        } else {
	            return ResponseEntity.ok(collections);
	        }
	    }

	@Override
	public List<Ecole> getAllEcole() {
		return ecolerepository.findAll();
	}
	

    public List<EcoleDto> CollectionEcoles() {
        String query = "SELECT a.idecole,UPPER(a.ecole) AS ecole,UPPER(a.avenue) AS avenue,e.idannee,"
        		+ " UPPER(e.annee) AS annee,d.idprovince,UPPER(d.province) AS province,"
        		+ " c.idintermedaireannee,c.idecole,c.idannee,b.idcommune,UPPER(b.commune) AS commune"
        		+ " FROM tab_Ecole a"
        		+ " JOIN tab_Intermedaireannee c ON c.idecole = a.idecole"
        		+ " JOIN tab_Annee e ON e.idannee = c.idannee"
        		+ " JOIN tab_Province d ON d.idprovince = a.idprovince"
        		+ " JOIN tab_Commune b ON b.idcommune = a.idcommune"
        		+ " ORDER BY c.idecole DESC";
        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(EcoleDto.class));
    }


    public ResponseEntity<?> CollectionEcole() {
        List<EcoleDto> collections = CollectionEcoles();
        
        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
        } else {
            return ResponseEntity.ok(collections);
        }
    }
    
    public List<EcoleDto> CollectionEcole(long idecole) {
        String query = "SELECT a.idecole,UPPER(a.ecole) AS ecole,UPPER(a.avenue) AS avenue,e.idannee,"
        		+ " UPPER(e.annee) AS annee,d.idprovince,UPPER(d.province) AS province,"
        		+ " c.idintermedaireannee,c.idecole,c.idannee,b.idcommune,UPPER(b.commune) AS commune "
        		+ " FROM tab_Ecole a"
        		+ " JOIN tab_Intermedaireannee c ON c.idecole = a.idecole"
        		+ " JOIN tab_Annee e ON e.idannee = c.idannee"
        		+ " JOIN tab_Province d ON d.idprovince = a.idprovince"
        		+ " JOIN tab_Commune b ON b.idcommune = a.idcommune"
        		+ " WHERE a.idecole = :idecole "
        		+ " ORDER BY c.idecole DESC";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("idecole", idecole);
            return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EcoleDto.class));
    }


    public ResponseEntity<?> CollectionEcoles(long idecole) {
        List<EcoleDto> collections = CollectionEcole(idecole);
        
        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
        } else {
            return ResponseEntity.ok(collections);
        }
    }

}

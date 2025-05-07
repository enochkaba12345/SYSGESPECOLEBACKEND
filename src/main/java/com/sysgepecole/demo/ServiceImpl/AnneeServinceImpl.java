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

import com.sysgepecole.demo.Dto.AnneeModelDto;
import com.sysgepecole.demo.Dto.PaiementDto;
import com.sysgepecole.demo.Models.Annee;
import com.sysgepecole.demo.Repository.AnneeRepository;
import com.sysgepecole.demo.Service.AnneeService;

@Service
public class AnneeServinceImpl implements AnneeService{
	
	@Autowired
	private AnneeRepository anneerepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	@Override
	public Optional<Annee> findAnneeByAnnee(String annee) {
		return anneerepository.findByAnnee(annee); 
	}

	@Override
	public Annee CreateAnnee(Annee annee) {
		Optional<Annee> existingEntity = findAnneeByAnnee(annee.getAnnee());
		if (existingEntity.isPresent()) {
			} else {	
				return anneerepository.save(annee); 
				}
		return annee;
	}

	@Override
	public ResponseEntity<Annee> updateAnnee(Long idannee, Annee annee) {
		Optional<Annee> AnneeData = anneerepository.findById(idannee);
		if (AnneeData.isPresent()) {
			Annee annees = AnneeData.get();
			annees.setIdannee(annee.getIdannee());
			annees.setAnnee(annee.getAnnee());
			annees.setIduser(annee.getIduser());
			return new ResponseEntity<>(anneerepository.save(annee), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Map<String, Boolean>> delete(Long idannee) {
		Optional<Annee> annee = anneerepository.findById(idannee);
		if (annee.isPresent()) {
			anneerepository.deleteById(idannee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

		
	
	public List<Annee> getAllAnnees() {
	    String query = "SELECT idannee, annee FROM tab_Annee ORDER BY annee ASC";
	    return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(Annee.class));
	}
	
	public ResponseEntity<?> getAllAnnee() {
		  List<Annee> collections = getAllAnnees();

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune province trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	
	public List<AnneeModelDto> CollecteAnnees( Long idintermedaireclasse) {
	    String query = "SELECT a.idecole, UPPER(a.ecole) AS ecole, e.idannee, "
	                 + "UPPER(e.annee) AS annee, c.idintermedaireannee,b.idintermedaireclasse,"
	                 + " d.idclasse, UPPER(d.classe) AS classe "
	                 + "FROM tab_Ecole a "
	                 + "JOIN tab_Intermedaireannee c ON c.idecole = a.idecole "
	                 + "JOIN tab_Annee e ON e.idannee = c.idannee "
	                 + "JOIN tab_Intermedaireclasse b ON b.idecole = a.idecole "
	                 + "JOIN tab_Classe d ON b.idclasse = d.idclasse "
	                 + "WHERE b.idintermedaireclasse = :idintermedaireclasse "
	                 + "ORDER BY e.idannee DESC";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("idintermedaireclasse", idintermedaireclasse);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(AnneeModelDto.class));
	}

	@Override
	public ResponseEntity<?> CollecteAnnee(long idintermedaireclasse) {
	    List<AnneeModelDto> collections = CollecteAnnees(idintermedaireclasse);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun niveau trouvé.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}

	public List<PaiementDto> CollecteCategorieAnnees(long idtranche,long idcategorie ,long idecole) {
		String query = "SELECT  DISTINCT "
				+ " h.idannee, UPPER(h.annee) AS annee ,d.idintermedaireannee "
				+ " FROM tab_Frais f "
				+ " JOIN tab_Intermedaireannee d ON d.idintermedaireannee = f.idintermedaireannee "
				+ " JOIN tab_Annee h ON h.idannee = d.idannee "
				+ " JOIN tab_Ecole e ON e.idecole = d.idecole "
				+ " WHERE f.idtranche = idtranche AND f.idcategorie = :idcategorie AND e.idecole = :idecole";
	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	    		.addValue("idtranche", idtranche)
	    		.addValue("idcategorie", idcategorie)
	    		.addValue("idecole", idecole);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}
	
	public ResponseEntity<?> CollecteCategorieAnnee(long idtranche,long idcategorie,long idecole) {
		  List<PaiementDto> collections = CollecteCategorieAnnees(idtranche,idcategorie,idecole);

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune Categorie trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	
	public List<PaiementDto> CollecteCategorieAnneeAdmins(long idtranche,long idcategorie) {
		String query = "SELECT  DISTINCT "
				+ " h.idannee, UPPER(h.annee) AS annee,d.idintermedaireannee  "
				+ " FROM tab_Frais f "
				+ " JOIN tab_Intermedaireannee d ON d.idintermedaireannee = f.idintermedaireannee "
				+ " JOIN tab_Annee h ON h.idannee = d.idannee "
				+ " WHERE f.idtranche = :idtranche AND f.idcategorie = :idcategorie ";
	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	    		.addValue("idtranche", idtranche)
	    		.addValue("idcategorie", idcategorie);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}
	
	public ResponseEntity<?> CollecteCategorieAnneeAdmin(long idtranche,long idcategorie) {
		  List<PaiementDto> collections = CollecteCategorieAnneeAdmins(idtranche,idcategorie);

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune Categorie trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	
	public List<AnneeModelDto> CollecteEcoleAnnees(long idecole) {
	    String query = " SELECT DISTINCT a.idecole, UPPER(a.ecole) AS ecole, e.idannee, UPPER(e.annee) AS annee, c.idintermedaireannee "
	    		+ " FROM tab_Ecole a "
	    		+ " JOIN tab_Intermedaireannee c ON c.idecole = a.idecole "
	    		+ " JOIN tab_Annee e ON e.idannee = c.idannee "
	    		+ " WHERE a.idecole = :idecole"
	    		+ " ORDER BY e.idannee DESC";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("idecole", idecole);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(AnneeModelDto.class));
	}

	@Override
	public ResponseEntity<?> CollecteEcoleAnnee(long idecole) {
	    List<AnneeModelDto> collections = CollecteEcoleAnnees(idecole);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun niveau trouvé.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}
	

}

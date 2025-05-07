package com.sysgepecole.demo.ServiceImpl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.sysgepecole.demo.Dto.PaiementDto;
import com.sysgepecole.demo.Models.Categorie_frais;
import com.sysgepecole.demo.Repository.CategorieRepository;
import com.sysgepecole.demo.Service.Categorie_fraisService;
@Service
public class Categorie_fraisServiceImpl implements Categorie_fraisService{
	
	@Autowired
	public CategorieRepository categorierepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public List<Categorie_frais> getAllCategories() {
	    String query = "SELECT idcategorie, categorie FROM Tab_categoriefrais ORDER BY idcategorie ASC";
	    return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(Categorie_frais.class));
	}
	
	public ResponseEntity<?> getAllCategorie() {
		  List<Categorie_frais> collections = getAllCategories();

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune Categorie trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	
	public List<PaiementDto> CollecteTrancheCategories(long idtranche) {
		String query = "SELECT  g.idcategorie, UPPER(g.categorie) AS categorie, SUM(f.montant) AS total_montant "
				+ " FROM tab_Frais f "
				+ " JOIN tab_Categoriefrais g ON g.idcategorie = f.idcategorie "
				+ " WHERE f.idtranche = :idtranche "
				+ " GROUP BY g.idcategorie, g.categorie "
				+ " ORDER BY g.idcategorie ASC ";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("idtranche", idtranche);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}
	
	public ResponseEntity<?> CollecteTrancheCategorie(long idtranche) {
		  List<PaiementDto> collections = CollecteTrancheCategories(idtranche);

		    if (collections.isEmpty()) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune Categorie trouvée.");
		    } else {
		        return ResponseEntity.ok(collections);
		    }
	}
	

}

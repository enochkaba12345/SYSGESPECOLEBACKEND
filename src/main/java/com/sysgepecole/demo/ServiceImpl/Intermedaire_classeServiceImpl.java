package com.sysgepecole.demo.ServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


import com.sysgepecole.demo.Dto.ClasseDto;
import com.sysgepecole.demo.Dto.EcoleDto;
import com.sysgepecole.demo.Dto.IntermedaireClasseDto;
import com.sysgepecole.demo.Exception.ResourceNotFoundException;
import com.sysgepecole.demo.Models.Classe;
import com.sysgepecole.demo.Models.Ecole;
import com.sysgepecole.demo.Models.Intermedaire_classe;
import com.sysgepecole.demo.Repository.ClasseRepository;
import com.sysgepecole.demo.Repository.EcoleRepository;
import com.sysgepecole.demo.Repository.Intermedaire_classeRepository;
import com.sysgepecole.demo.Service.Intermedaire_classeService;

@Service
public class Intermedaire_classeServiceImpl implements Intermedaire_classeService{
	
	@Autowired
	private EcoleRepository ecolerepository;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private Intermedaire_classeRepository intermediaireClasserepository;

	@Autowired
	private ClasseRepository classerepository;




	@Override
	public ResponseEntity<Map<String, Boolean>> delete(Long idintermedaireclasse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Intermedaire_classe> getAllIntermedaireclasse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Intermedaire_classe createIntermedaireClasse(IntermedaireClasseDto requestDto) {
		Long classeId = requestDto.getClasseDto().getIdclasse();
        if (classeId == null) {
            throw new IllegalArgumentException("L'ID de la classe ne doit pas être null");
        }

        Long ecoleId = requestDto.getEcoleDto().getIdecole();
        if (ecoleId == null) {
            throw new IllegalArgumentException("L'ID de l'école ne doit pas être null");
        }

        Classe classe = classerepository.findById(classeId)
            .orElseThrow(() -> new ResourceNotFoundException("Annee not found with id " + classeId));

        Ecole ecole = ecolerepository.findById(ecoleId)
            .orElseThrow(() -> new ResourceNotFoundException("Ecole not found with id " + ecoleId));

        Intermedaire_classe intermedaireClasse = new Intermedaire_classe();
        intermedaireClasse.setClasse(classe);
        intermedaireClasse.setEcole(ecole);
  

        return intermediaireClasserepository.save(intermedaireClasse);
	}
	
	 @Override
	    public Intermedaire_classe updateIntermedaireClasse(IntermedaireClasseDto requestDto) {
	        Long classeId = requestDto.getClasseDto().getIdclasse();
	        if (classeId == null) {
	            throw new IllegalArgumentException("L'ID de l'année ne doit pas être null");
	        }

	        Long ecoleId = requestDto.getEcoleDto().getIdecole();
	        if (ecoleId == null) {
	            throw new IllegalArgumentException("L'ID de l'école ne doit pas être null");
	        }

	        Classe classe = classerepository.findById(classeId)
	            .orElseThrow(() -> new ResourceNotFoundException("Annee not found with id " + classeId));

	        Ecole ecole = ecolerepository.findById(ecoleId)
	            .orElseThrow(() -> new ResourceNotFoundException("Ecole not found with id " + ecoleId));

	        Intermedaire_classe existingIntermedaireClasse = intermediaireClasserepository.findById(requestDto.getIdintermedaireclasse())
	            .orElseThrow(() -> new ResourceNotFoundException("IntermedaireClasse not found with id " + requestDto.getIdintermedaireclasse()));

	        existingIntermedaireClasse.setClasse(classe);
	        existingIntermedaireClasse.setEcole(ecole);
	

	        return intermediaireClasserepository.save(existingIntermedaireClasse);
	    }
	 
	 @Override
		public Optional<Intermedaire_classe> getIntermedaireClasseById(Long idintermedaireclasse) {
			return intermediaireClasserepository.findById(idintermedaireclasse);
		}
		
		    
		@Override
		public List<IntermedaireClasseDto> getInterecoles() {
		    String query = "SELECT DISTINCT a.idecole, UPPER(a.ecole) AS ecole "
		    		+ "	FROM tab_Ecole a "
		    		+ "	JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole "
		    		+ " JOIN tab_Classe e ON c.idclasse = e.idclasse "
		    		+ " JOIN tab_Intermedaireannee d1 ON a.idecole = d1.idecole "
		    		+ " JOIN tab_Annee f ON d1.idannee = f.idannee "
		    		+ "	JOIN tab_Niveau d ON e.idniveau = d.idniveau "
		    		+ "	ORDER BY ecole DESC ";

		    return namedParameterJdbcTemplate.query(query, (rs, rowNum) -> {
		        IntermedaireClasseDto intermedaireClasseDto = new IntermedaireClasseDto();
		     

		        EcoleDto ecoleDto = new EcoleDto();
		        ecoleDto.setIdecole(rs.getLong("idecole"));
		        ecoleDto.setEcole(rs.getString("ecole"));

		        ClasseDto classeDto = new ClasseDto();

		        intermedaireClasseDto.setEcoleDto(ecoleDto);
		        intermedaireClasseDto.setClasseDto(classeDto);

		        return intermedaireClasseDto;
		    });
		}

		

		    public ResponseEntity<?> GetInterecole() {
		        List<IntermedaireClasseDto> collections = getInterecoles();
		        
		        if (collections.isEmpty()) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
		        } else {
		            return ResponseEntity.ok(collections);
		        }
		    }
		    
		    
		    @Override
		    public List<IntermedaireClasseDto> getInterecoleNoAdmins(Long idecole) { 
		        String query = "SELECT DISTINCT a.idecole, UPPER(a.ecole) AS ecole "
		                     + " FROM tab_Ecole a "
		                     + " JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole "
		                     + " JOIN tab_Classe e ON c.idclasse = e.idclasse "
		                     + " JOIN tab_Intermedaireannee d1 ON a.idecole = d1.idecole "
		                     + " JOIN tab_Annee f ON d1.idannee = f.idannee "
		                     + " JOIN tab_Niveau d ON e.idniveau = d.idniveau ";

		        MapSqlParameterSource params = new MapSqlParameterSource();
		        
		        if (idecole != null) { 
		            query += " WHERE a.idecole = :idecole";
		            params.addValue("idecole", idecole);
		        }

		        query += " ORDER BY ecole DESC";

		        return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
		            IntermedaireClasseDto intermedaireClasseDto = new IntermedaireClasseDto();

		            EcoleDto ecoleDto = new EcoleDto();
		            ecoleDto.setIdecole(rs.getLong("idecole"));
		            ecoleDto.setEcole(rs.getString("ecole"));

		            ClasseDto classeDto = new ClasseDto();

		            intermedaireClasseDto.setEcoleDto(ecoleDto);
		            intermedaireClasseDto.setClasseDto(classeDto);

		            return intermedaireClasseDto;
		        });
		    }


			    public ResponseEntity<?> GetInterecoleNoAdmin(long idecole) {
			        List<IntermedaireClasseDto> collections = getInterecoleNoAdmins(idecole);
			        
			        if (collections.isEmpty()) {
			            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
			        } else {
			            return ResponseEntity.ok(collections);
			        }
			    }
			    
		    
		    @Override
			public List<IntermedaireClasseDto> getInterniveaux(long idecole) {
			    String query = "SELECT DISTINCT  a.idecole, UPPER(a.ecole) AS ecole,"
			    		+ "	d.idniveau, UPPER(d.niveau) AS niveau "
			    		+ "	FROM tab_Ecole a "
			    		+ "	JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole "
			    		+ "	JOIN tab_Classe e ON c.idclasse = e.idclasse "
			    		+ "	JOIN tab_Intermedaireannee d1 ON a.idecole = d1.idecole "
			    		+ "	JOIN tab_Annee f ON d1.idannee = f.idannee "
			    		+ "	JOIN tab_Niveau d ON e.idniveau = d.idniveau "
			    		+ "	WHERE c.idecole = :idecole"
			    		+ "	ORDER BY niveau DESC ";
			    
			    MapSqlParameterSource params = new MapSqlParameterSource();
			    params.addValue("idecole", idecole);

			    return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
			        
			        EcoleDto ecoleDto = new EcoleDto();
			        ecoleDto.setIdecole(rs.getLong("idecole"));
			        ecoleDto.setEcole(rs.getString("ecole"));

			        ClasseDto classeDto = new ClasseDto();
			        classeDto.setIdniveau(rs.getLong("idniveau"));
			        classeDto.setNiveau(rs.getString("niveau"));

			        IntermedaireClasseDto intermedaireClasseDto = new IntermedaireClasseDto();
			        intermedaireClasseDto.setEcoleDto(ecoleDto);
			        intermedaireClasseDto.setClasseDto(classeDto);

			        return intermedaireClasseDto;
			    });
			}

			    public ResponseEntity<?> getInterniveau(long idecole) {
			        List<IntermedaireClasseDto> collections = getInterniveaux( idecole);
			        
			        if (collections.isEmpty()) {
			            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
			        } else {
			            return ResponseEntity.ok(collections);
			        }
			    }
		    
		    @Override
		    public List<IntermedaireClasseDto> getIdInterclasses(long idintermedaireclasse) {
		        String query ="SELECT a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, " +
		                   "UPPER(e.classe) AS classe, c.idintermedaireclasse, d.idniveau, UPPER(d.niveau) AS niveau " +
		                   "FROM tab_Ecole a " +
		                   "JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole " +
		                   "JOIN tab_Classe e ON e.idclasse = c.idclasse " +
		                   "JOIN tab_Niveau d ON e.idniveau = d.idniveau " +
		                   "WHERE c.idintermedaireclasse = :idintermedaireclasse " +
		                   "ORDER BY a.ecole DESC";

		        MapSqlParameterSource params = new MapSqlParameterSource();
		        params.addValue("idintermedaireclasse", idintermedaireclasse);

		        return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
		        	IntermedaireClasseDto intermedaireClasseDto = new IntermedaireClasseDto();
		        	intermedaireClasseDto.setIdintermedaireclasse(rs.getLong("idintermedaireclasse"));

		            EcoleDto ecoleDto = new EcoleDto();
		            ecoleDto.setIdecole(rs.getLong("idecole"));
		            ecoleDto.setEcole(rs.getString("ecole"));
		       

		            ClasseDto classeDto = new ClasseDto();
		            classeDto.setIdclasse(rs.getLong("idclasse"));
		            classeDto.setIdclasse(rs.getLong("idniveau"));
			        classeDto.setClasse(rs.getString("classe"));
			        classeDto.setNiveau(rs.getString("niveau"));

		            intermedaireClasseDto.setEcoleDto(ecoleDto);
		            intermedaireClasseDto.setClasseDto(classeDto);

		            return intermedaireClasseDto;
		        });
		    }

			    public ResponseEntity<?> getIdInterclasse(long idintermedaireclasse) {
			        List<IntermedaireClasseDto> collections = getIdInterclasses(idintermedaireclasse);
			        
			        if (collections.isEmpty()) {
			            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
			        } else {
			            return ResponseEntity.ok(collections);
			        }
			    }

				@Override
				public List<IntermedaireClasseDto> getniveauclasses(long idecole,long idniveau) {
					 String query ="SELECT a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, " +
			                   "UPPER(e.classe) AS classe, c.idintermedaireclasse, d.idniveau, UPPER(d.niveau) AS niveau " +
			                   "FROM tab_Ecole a " +
			                   "JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole " +
			                   "JOIN tab_Classe e ON e.idclasse = c.idclasse " +
			                   "JOIN tab_Niveau d ON e.idniveau = d.idniveau " +
			                   "WHERE a.idecole = :idecole  AND d.idniveau = :idniveau " +
			                   "ORDER BY e.classe DESC";

			        MapSqlParameterSource params = new MapSqlParameterSource();
			        params.addValue("idecole", idecole)
			        .addValue("idniveau", idniveau);

			        return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
			        	IntermedaireClasseDto intermedaireClasseDto = new IntermedaireClasseDto();
			        	intermedaireClasseDto.setIdintermedaireclasse(rs.getLong("idintermedaireclasse"));

			            EcoleDto ecoleDto = new EcoleDto();
			            ecoleDto.setIdecole(rs.getLong("idecole"));
			            ecoleDto.setEcole(rs.getString("ecole"));
			       

			            ClasseDto classeDto = new ClasseDto();
			            classeDto.setIdclasse(rs.getLong("idclasse"));
			            classeDto.setIdniveau(rs.getLong("idniveau"));
				        classeDto.setClasse(rs.getString("classe"));
				        classeDto.setNiveau(rs.getString("niveau"));

			            intermedaireClasseDto.setEcoleDto(ecoleDto);
			            intermedaireClasseDto.setClasseDto(classeDto);

			            return intermedaireClasseDto;
			        });
			    }

				  public ResponseEntity<?> getniveauclasse(long idecole,long idniveau) {
				        List<IntermedaireClasseDto> collections = getniveauclasses(idecole,idniveau);
				        
				        if (collections.isEmpty()) {
				            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
				        } else {
				            return ResponseEntity.ok(collections);
				        }
				    }


	    

}

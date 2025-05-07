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

import com.sysgepecole.demo.Dto.AnneeDto;
import com.sysgepecole.demo.Dto.EcoleDto;
import com.sysgepecole.demo.Dto.IntermedaireAnneeDto;
import com.sysgepecole.demo.Exception.ResourceNotFoundException;
import com.sysgepecole.demo.Models.Annee;
import com.sysgepecole.demo.Models.Ecole;
import com.sysgepecole.demo.Models.Intermedaire_annee;
import com.sysgepecole.demo.Repository.AnneeRepository;
import com.sysgepecole.demo.Repository.EcoleRepository;
import com.sysgepecole.demo.Repository.Intermedaire_anneeRepository;
import com.sysgepecole.demo.Service.Intermedaire_anneeService;


@Service
public class Intermedaire_anneeServiceImpl implements Intermedaire_anneeService {

	@Autowired
	private EcoleRepository ecolerepository;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private Intermedaire_anneeRepository intermediaireAnneerepository;

	@Autowired
	private AnneeRepository anneerepository;

	
	

	@Override
	public ResponseEntity<Map<String, Boolean>> delete(Long idintermedaireannee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Intermedaire_annee> getAllIntermedaireannee() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    public Intermedaire_annee createIntermedaireAnnee(IntermedaireAnneeDto requestDto) {
        Long anneeId = requestDto.getAnneeDto().getIdannee();
        if (anneeId == null) {
            throw new IllegalArgumentException("L'ID de l'année ne doit pas être null");
        }

        Long ecoleId = requestDto.getEcoleDto().getIdecole();
        if (ecoleId == null) {
            throw new IllegalArgumentException("L'ID de l'école ne doit pas être null");
        }

        Annee annee = anneerepository.findById(anneeId)
            .orElseThrow(() -> new ResourceNotFoundException("Annee not found with id " + anneeId));

        Ecole ecole = ecolerepository.findById(ecoleId)
            .orElseThrow(() -> new ResourceNotFoundException("Ecole not found with id " + ecoleId));

        Intermedaire_annee intermedaireAnnee = new Intermedaire_annee();
        intermedaireAnnee.setAnnee(annee);
        intermedaireAnnee.setEcole(ecole);
        return intermediaireAnneerepository.save(intermedaireAnnee);
    }

    @Override
    public Intermedaire_annee updateIntermedaireAnnee(IntermedaireAnneeDto requestDto) {
        Long anneeId = requestDto.getAnneeDto().getIdannee();
        if (anneeId == null) {
            throw new IllegalArgumentException("L'ID de l'année ne doit pas être null");
        }

        Long ecoleId = requestDto.getEcoleDto().getIdecole();
        if (ecoleId == null) {
            throw new IllegalArgumentException("L'ID de l'école ne doit pas être null");
        }

        Annee annee = anneerepository.findById(anneeId)
            .orElseThrow(() -> new ResourceNotFoundException("Annee not found with id " + anneeId));

        Ecole ecole = ecolerepository.findById(ecoleId)
            .orElseThrow(() -> new ResourceNotFoundException("Ecole not found with id " + ecoleId));

        Intermedaire_annee existingIntermedaireAnnee = intermediaireAnneerepository.findById(requestDto.getIdintermedaireannee())
            .orElseThrow(() -> new ResourceNotFoundException("IntermedaireAnnee not found with id " + requestDto.getIdintermedaireannee()));

        existingIntermedaireAnnee.setAnnee(annee);
        existingIntermedaireAnnee.setEcole(ecole);

        return intermediaireAnneerepository.save(existingIntermedaireAnnee);
    }
    
	
	
	@Override
	public Optional<Intermedaire_annee> getIntermedaireAnneeById(Long idintermedaireannee) {
		return intermediaireAnneerepository.findById(idintermedaireannee);
	}
	
	    
	@Override
	public List<IntermedaireAnneeDto> getInterannees() {
	    String query = "SELECT a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idannee, " +
	                   "UPPER(e.annee) AS annee, c.idintermedaireannee " +
	                   "FROM tab_Ecole a " +
	                   "JOIN tab_Intermedaireannee c ON c.idecole = a.idecole " +
	                   "JOIN tab_Annee e ON e.idannee = c.idannee " +
	                   "ORDER BY a.ecole DESC";

	    return namedParameterJdbcTemplate.query(query, (rs, rowNum) -> {
	        IntermedaireAnneeDto intermedaireAnneeDto = new IntermedaireAnneeDto();
	        intermedaireAnneeDto.setIdintermedaireannee(rs.getLong("idintermedaireannee"));

	        EcoleDto ecoleDto = new EcoleDto();
	        ecoleDto.setIdecole(rs.getLong("idecole"));
	        ecoleDto.setEcole(rs.getString("ecole"));

	        AnneeDto anneeDto = new AnneeDto();
	        anneeDto.setIdannee(rs.getLong("idannee"));
	      

	        intermedaireAnneeDto.setEcoleDto(ecoleDto);
	        intermedaireAnneeDto.setAnneeDto(anneeDto);

	        return intermedaireAnneeDto;
	    });
	}

	    public ResponseEntity<?> GetInterannee() {
	        List<IntermedaireAnneeDto> collections = getInterannees();
	        
	        if (collections.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
	        } else {
	            return ResponseEntity.ok(collections);
	        }
	    }
	    
	    @Override
	    public List<IntermedaireAnneeDto> getrannees(long idecole) {
	        String query = "SELECT a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idannee, " +
	                       "UPPER(e.annee) AS annee, c.idintermedaireannee " +
	                       "FROM tab_Ecole a " +
	                       "JOIN tab_Intermedaireannee c ON c.idecole = a.idecole " +
	                       "JOIN tab_Annee e ON e.idannee = c.idannee " +
	                       "WHERE a.idecole = :idecole " +
	                       "ORDER BY a.ecole DESC";

	        
	        MapSqlParameterSource params = new MapSqlParameterSource();
	        params.addValue("idecole", idecole);
	        System.out.println("Query Parameters: " + params); 

	        // Execute query
	        return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
	            IntermedaireAnneeDto intermedaireAnneeDto = new IntermedaireAnneeDto();
	            intermedaireAnneeDto.setIdintermedaireannee(rs.getLong("idintermedaireannee"));

	            EcoleDto ecoleDto = new EcoleDto();
	            ecoleDto.setIdecole(rs.getLong("idecole"));
	            ecoleDto.setEcole(rs.getString("ecole"));

	            AnneeDto anneeDto = new AnneeDto();
	            anneeDto.setIdannee(rs.getLong("idannee"));

	            intermedaireAnneeDto.setEcoleDto(ecoleDto);
	            intermedaireAnneeDto.setAnneeDto(anneeDto);

	            return intermedaireAnneeDto;
	        });
	    }

	    
	    @Override
	    public List<IntermedaireAnneeDto> getIdInterannees(long idintermedaireannee) {
	        String query = "SELECT a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idannee, " +
	                       "UPPER(e.annee) AS annee, c.idintermedaireannee " +
	                       "FROM tab_Ecole a " +
	                       "JOIN tab_Intermedaireannee c ON c.idecole = a.idecole " +
	                       "JOIN tab_Annee e ON e.idannee = c.idannee " +
	                       "WHERE c.idintermedaireannee = :idintermedaireannee " +
	                       "ORDER BY a.ecole DESC";

	        MapSqlParameterSource params = new MapSqlParameterSource();
	        params.addValue("idintermedaireannee", idintermedaireannee);

	        return namedParameterJdbcTemplate.query(query, params, (rs, rowNum) -> {
	            IntermedaireAnneeDto intermedaireAnneeDto = new IntermedaireAnneeDto();
	            intermedaireAnneeDto.setIdintermedaireannee(rs.getLong("idintermedaireannee"));

	            EcoleDto ecoleDto = new EcoleDto();
	            ecoleDto.setIdecole(rs.getLong("idecole"));
	            ecoleDto.setEcole(rs.getString("ecole"));
	       

	            AnneeDto anneeDto = new AnneeDto();
	            anneeDto.setIdannee(rs.getLong("idannee"));
	         

	            intermedaireAnneeDto.setEcoleDto(ecoleDto);
	            intermedaireAnneeDto.setAnneeDto(anneeDto);

	            return intermedaireAnneeDto;
	        });
	    }

		    public ResponseEntity<?> getIdInterannee(long idintermedaireannee) {
		        List<IntermedaireAnneeDto> collections = getIdInterannees(idintermedaireannee);
		        
		        if (collections.isEmpty()) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
		        } else {
		            return ResponseEntity.ok(collections);
		        }
		    }


}

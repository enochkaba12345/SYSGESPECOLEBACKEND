package com.sysgepecole.demo.ServiceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sysgepecole.demo.Dto.EleveModelDto;
import com.sysgepecole.demo.Dto.EleveUpdateDTO;
import com.sysgepecole.demo.Models.Eleve;
import com.sysgepecole.demo.Models.reportBase64;
import com.sysgepecole.demo.Repository.EleveRepository;

import com.sysgepecole.demo.Service.EleveService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



@Service
public class EleveServiceImpl implements EleveService{
	
	private static final Logger logger = LoggerFactory.getLogger(EleveServiceImpl.class);

	
	@Autowired
	private EleveRepository eleverepository;
	
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	


	
	@Override
	public Optional<Eleve> findEleveByNomPostnomPrenom(String nom, String postnom, String prenom,Long idintermedaireclasse,Long idintermedaireannee) {
		return eleverepository.findByNomAndPostnomAndPrenomAndIdintermedaireclasseAndIdintermedaireannee(nom, postnom, prenom,idintermedaireclasse,idintermedaireannee); 
	}
	
	
	@Override
	public Eleve createEleve(Eleve eleve) {
		Optional<Eleve> eleves = findEleveByNomPostnomPrenom(eleve.getNom(),eleve.getPostnom(),eleve.getPrenom(),eleve.getIdintermedaireclasse(),eleve.getIdintermedaireannee());
		if (eleves.isPresent()) {
			} else {	
				return eleverepository.save(eleve); 
				}
		return eleve;
	}
	    
	
	
	@Override
	public ResponseEntity<Eleve> updateEleve(Long ideleve, Eleve eleve) {
		Optional<Eleve> EleveData = eleverepository.findById(ideleve);
		if (EleveData.isPresent()) {
			Eleve eleves = EleveData.get();
			eleves.setNom(eleve.getNom());
	        eleves.setPostnom(eleve.getPostnom());
	        eleves.setPrenom(eleve.getPrenom());
	        eleves.setSexe(eleve.getSexe());
	        eleves.setIdprovince(eleve.getIdprovince());
	        eleves.setDatenaiss(eleve.getDatenaiss());
	        eleves.setTelephone(eleve.getTelephone());
	        eleves.setIdeleve(eleve.getIdeleve());
	        eleves.setEmail(eleve.getEmail());
	        eleves.setDateins(eleve.getDateins());
	        eleves.setAdresse(eleve.getAdresse());
	        eleves.setIdintermedaireclasse(eleve.getIdintermedaireclasse());
	        eleves.setIdintermedaireannee(eleve.getIdintermedaireannee());
	        eleves.setIduser(eleve.getIduser());
			return new ResponseEntity<>(eleverepository.save(eleve), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	

	  public List<EleveModelDto> CollecteEleves() {
	        String query = "SELECT b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom,"
	                + "UPPER(b.sexe) AS sexe, UPPER(b.nomtuteur) AS nomtuteur, b.dateins, b.datenaiss, UPPER(b.email) AS email,"
	                + "b.telephone, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe,UPPER(b.adresse) AS adresse,"
	                + "c.idintermedaireclasse, d.idintermedaireannee, g.idprovince, UPPER(g.province) AS province,"
	                + "h.idcommune, UPPER(h.commune) AS commune, f.idannee, UPPER(f.annee) AS annee,UPPER(a.avenue) AS avenue,"
			+ " COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos "
	                + " FROM tab_Eleve b"
	                + " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse"
	                + " JOIN tab_Classe e ON c.idclasse = e.idclasse"
	                + " JOIN tab_Ecole a ON c.idecole = a.idecole"
	                + " JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee"
	                + " JOIN tab_Annee f ON d.idannee = f.idannee"
	                + " JOIN tab_Province g ON b.idprovince = g.idprovince"
	                + " JOIN tab_Commune h ON h.idcommune = a.idcommune"
	                + " LEFT JOIN tab_Logos x ON x.idecole = a.idecole "
	                + " ORDER BY b.ideleve DESC limit 1";
	        return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(EleveModelDto.class));
	    }
	    

    public ResponseEntity<?> CollecteEleve() {
        List<EleveModelDto> collections = CollecteEleves();
        

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
        } else {
            return ResponseEntity.ok(collections);
        }
    }
    
    public List<EleveModelDto> CollecteElevese(long idecole) {
        String query = "SELECT UPPER(e.classe) AS classe, UPPER(f.annee) AS annee, COUNT(b.ideleve) AS ideleve "
        		+ "FROM tab_Eleve b "
        		+ "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
        		+ "JOIN tab_Classe e ON c.idclasse = e.idclasse "
        		+ "JOIN tab_Ecole a ON c.idecole = a.idecole "
        		+ "JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee "
        		+ "JOIN tab_Annee f ON d.idannee = f.idannee "
        		+ "JOIN tab_Province g ON b.idprovince = g.idprovince "
        		+ "JOIN tab_Commune h ON h.idcommune = a.idcommune "
        		+ " JOIN tab_Ecole k ON k.idecole = d.idecole "
    	 		+ "	WHERE k.idecole = :idecole  "
        		+ "GROUP BY e.classe,f.annee "
        		+ "ORDER BY annee ASC";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
		   		.addValue("idecole", idecole);
     
    
    try {
        return namedParameterJdbcTemplate.query(query,parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
        	  } catch (Exception e) {
      return Collections.emptyList();
  }
}
    

public ResponseEntity<?> CollecteEleveses(long idecole) {
    List<EleveModelDto> collections = CollecteElevese(idecole);
    

    if (collections.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
    } else {
        return ResponseEntity.ok(collections);
    }
}
    
    public List<EleveModelDto> FicheEleves(Long ideleve) {
  
    	String query = "SELECT b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom,"
    			+ "UPPER(b.sexe) AS sexe, UPPER(b.nomtuteur) AS nomtuteur, b.dateins, b.datenaiss, UPPER(b.email) AS email,"
    			+ "b.telephone, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe,UPPER(b.adresse) AS adresse,"
    			+ "c.idintermedaireclasse, d.idintermedaireannee, g.idprovince, UPPER(g.province) AS province, x.id,y.id, "
    			+ "h.idcommune, UPPER(h.commune) AS commune, f.idannee, UPPER(f.annee) AS annee,UPPER(a.avenue) AS avenue,UPPER(z.username) AS username, "
    			+ " COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos, "
    			+ " COALESCE(NULLIF(y.photo, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747291830/icon_jygejr.jpg') AS photo " 
    			+ " FROM tab_Eleve b"
    			+ "	JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse"
    			+ "	JOIN tab_Classe e ON c.idclasse = e.idclasse"
    			+ " JOIN tab_Ecole a ON c.idecole = a.idecole"
    			+ "	JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee"
    			+ "	JOIN tab_Annee f ON d.idannee = f.idannee"
    			+ "	JOIN tab_User z ON z.iduser = b.iduser"
    			+ " JOIN tab_Province g ON b.idprovince = g.idprovince"
    			+ " JOIN tab_Commune h ON h.idcommune = a.idcommune"
    			+ " LEFT JOIN tab_Logos x ON x.idecole = a.idecole "
    			+ " LEFT JOIN tab_Photo y ON y.ideleve = b.ideleve "
                + " where b.ideleve = :ideleve " 
                + " ORDER BY b.ideleve limit 1";
    	 MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ideleve", ideleve);
	  
	    try {
	    	  return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
	    		  } catch (Exception e) {
	        return Collections.emptyList();
	    }
    }
	
	
    public ResponseEntity<?> FicheEleve(Long ideleve) throws FileNotFoundException, JRException {
        try {
            List<EleveModelDto> collections = FicheEleves(ideleve);
            if (collections.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Aucune fiche élève trouvée pour l'ID : " + ideleve);
            }

            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);
            JasperPrint reportlist = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(
                    ResourceUtils.getFile("classpath:etats/Eleves.jrxml").getAbsolutePath()
                ), null, ds
            );

            String encodedString = Base64.getEncoder()
                    .encodeToString(JasperExportManager.exportReportToPdf(reportlist));
            
            return ResponseEntity.ok(new reportBase64(encodedString));

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Fichier JRXML introuvable : " + e.getMessage());
        } catch (JRException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur JasperReports : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur inattendue : " + e.getMessage());
        }
    }

	
	
	
	public List<EleveModelDto> searchEleves(String nom, Long idecole, boolean isAdmin) {
	    String query = "SELECT DISTINCT b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, "
	                 + "UPPER(b.sexe) AS sexe, UPPER(b.nomtuteur) AS nomtuteur, b.dateins, b.datenaiss, UPPER(b.email) AS email, "
	                 + "b.telephone, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, UPPER(b.adresse) AS adresse, "
	                 + "c.idintermedaireclasse, d.idintermedaireannee, g.idprovince, UPPER(g.province) AS province, f.idannee, "
	                 + "UPPER(f.annee) AS annee, UPPER(a.avenue) AS avenue, x.id, "
	                + " COALESCE(NULLIF(x.photo, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747291830/icon_jygejr.jpg') AS photo " 
	                 + "FROM tab_Eleve b "
	                 + "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	                 + "JOIN tab_Classe e ON c.idclasse = e.idclasse "
	                 + "JOIN tab_Ecole a ON c.idecole = a.idecole "
	                 + "JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee "
	                 + "JOIN tab_Annee f ON d.idannee = f.idannee "
	                 + "LEFT JOIN tab_Photo x ON x.ideleve = b.ideleve "
	                 + "JOIN tab_Province g ON b.idprovince = g.idprovince "
	                 + "WHERE TRIM(LOWER(b.nom)) LIKE :nom ";

	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	            .addValue("nom", "%" + nom.toLowerCase().trim() + "%");

	    if (!isAdmin) { 
	        query += " AND a.idecole = :idecole";
	        parameters.addValue("idecole", idecole);
	    }

	    query += " ORDER BY b.ideleve";

	    try {
	        return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
	    } catch (Exception e) {
	        return Collections.emptyList();
	    }
	}


	
	public ResponseEntity<?> searchEleves(String userRole, String nom, Long idecole) {
	    boolean isAdmin = "ADMIN".equalsIgnoreCase(userRole);
	    
	    List<EleveModelDto> collections = searchEleves(nom, idecole, isAdmin);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé.");
	    }
	    return ResponseEntity.ok(collections);
	}

	
	public List<EleveModelDto> CollecteAnneeEleves(long idintermedaireclasse, long idintermedaireannee) {
	  

	    String query = "SELECT DISTINCT b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, "
	            + " UPPER(b.sexe) AS sexe, UPPER(b.nomtuteur) AS nomtuteur, b.dateins, b.datenaiss, UPPER(b.email) AS email, "
	            + " b.telephone, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, UPPER(b.adresse) AS adresse, "
	            + " c.idintermedaireclasse, d.idintermedaireannee, g.idprovince, UPPER(g.province) AS province, f.idannee, "
	            + " UPPER(f.annee) AS annee, UPPER(a.avenue) AS avenue, x.id, "
	            + " COALESCE(NULLIF(y.photo, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747291830/icon_jygejr.jpg') AS photo " 
	            + " FROM tab_Eleve b "
	            + " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	            + " JOIN tab_Classe e ON c.idclasse = e.idclasse "
	            + " JOIN tab_Ecole a ON c.idecole = a.idecole "
	            + " JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee "
	            + " JOIN tab_Annee f ON d.idannee = f.idannee "
	            + " LEFT JOIN tab_Photo x ON x.ideleve = b.ideleve "
	            + " JOIN tab_Province g ON b.idprovince = g.idprovince "
	            + " WHERE c.idintermedaireclasse = :idintermedaireclasse AND d.idintermedaireannee = :idintermedaireannee "
	            + " ORDER BY b.ideleve;";

	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	            .addValue("idintermedaireclasse", idintermedaireclasse)
	            .addValue("idintermedaireannee", idintermedaireannee);

	    try {
	        return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return Collections.emptyList(); 
	    }
	}



	public ResponseEntity<?> CollecteAnneeEleve(long idintermedaireclasse,long idintermedaireannee) {
	    List<EleveModelDto> collections = CollecteAnneeEleves(idintermedaireclasse,idintermedaireannee);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ce nom.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}
	
	public List<EleveModelDto> CollecteClasseAnneeEleves(long idintermedaireclasse,long idintermedaireannee) {
	    String query = "SELECT DISTINCT b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom,"
	    		+ "  a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, UPPER(b.adresse) AS adresse,"
	    		+ " c.idintermedaireclasse, d.idintermedaireannee, f.idannee, UPPER(f.annee) AS annee"
	    		+ " FROM tab_Eleve b"
	    		+ " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse"
	    		+ " JOIN tab_Classe e ON c.idclasse = e.idclasse"
	    		+ " JOIN tab_Ecole a ON c.idecole = a.idecole"
	    		+ " JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee"
	    		+ " JOIN tab_Annee f ON d.idannee = f.idannee"
	    		+ " WHERE  c.idintermedaireclasse = :idintermedaireclasse and d.idintermedaireannee = :idintermedaireannee"
	    		+ " GROUP BY b.ideleve, b.nom, b.postnom, b.prenom,"
	    		+ " a.idecole, a.ecole, e.idclasse, e.classe, c.idintermedaireclasse, d.idintermedaireannee, f.idannee, f.annee, a.avenue"
	    		+ " ORDER BY b.ideleve";
	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	    		.addValue("idintermedaireclasse", idintermedaireclasse)
	    		.addValue("idintermedaireannee", idintermedaireannee);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
	}

	public ResponseEntity<?> CollecteClasseAnneeEleve(long idintermedaireclasse,long idintermedaireannee) {
	    List<EleveModelDto> collections = CollecteClasseAnneeEleves(idintermedaireclasse,idintermedaireannee);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ce nom.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}

	public List<EleveModelDto> EleveParClasses(long idecole, long idclasse, long idannee) {
	  

	    String query = "SELECT DISTINCT b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, "
	            + " UPPER(b.sexe) AS sexe, UPPER(b.nomtuteur) AS nomtuteur, b.dateins, b.datenaiss, UPPER(b.email) AS email, "
	            + " b.telephone, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, UPPER(b.adresse) AS adresse, "
	            + " c.idintermedaireclasse, d.idintermedaireannee, g.idprovince, UPPER(g.province) AS province, f.idannee, "
	            + " UPPER(f.annee) AS annee, UPPER(a.avenue) AS avenue, x.id, "
	            + " COALESCE(NULLIF(x.photo, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747291830/icon_jygejr.jpg') AS photo " 
	            + " FROM tab_Eleve b "
	            + " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	            + " JOIN tab_Classe e ON c.idclasse = e.idclasse "
	            + " JOIN tab_Ecole a ON c.idecole = a.idecole "
	            + " JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee "
	            + " JOIN tab_Annee f ON d.idannee = f.idannee "
	            + " LEFT JOIN tab_Photo x ON x.ideleve = b.ideleve "
	            + " JOIN tab_Province g ON b.idprovince = g.idprovince "
	            + "WHERE a.idecole = :idecole AND e.idclasse = :idclasse AND f.idannee = :idannee "
	            + " ORDER BY b.ideleve;";

	    MapSqlParameterSource parameters = new MapSqlParameterSource()
		        .addValue("idecole", idecole)
		        .addValue("idclasse", idclasse)
		        .addValue("idannee", idannee);

	    try {
	        return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return Collections.emptyList(); 
	    }
	}
  

	public ResponseEntity<?> EleveParClasse(long idecole, long idclasse, long idannee) {
	    List<EleveModelDto> collections = EleveParClasses(idecole, idclasse,idannee);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}

	public List<EleveModelDto> ElevePars(long idecole, long idclasse, long idannee, long ideleve) {
	    String query = " SELECT  b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, "
	                 + " UPPER(b.sexe) AS sexe, UPPER(b.adresse) AS adresse, b.telephone,"
	                 + " a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, "
	                 + " c.idintermedaireclasse, d.idintermedaireannee, f.idannee, UPPER(f.annee) AS annee, x.id, "
	         	 + " COALESCE(NULLIF(y.photo, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747291830/icon_jygejr.jpg') AS photo "  
	                 + " FROM tab_Eleve b "
	                 + " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	                 + " JOIN tab_Classe e ON c.idclasse = e.idclasse "
	                 + " JOIN tab_Ecole a ON c.idecole = a.idecole "
	                 + " JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee "
	                 + " JOIN tab_Annee f ON d.idannee = f.idannee "
	                 + " LEFT JOIN tab_Photo x ON x.ideleve = b.ideleve "
	                 + " WHERE a.idecole = :idecole AND e.idclasse = :idclasse AND f.idannee = :idannee AND b.ideleve = :ideleve "
	                 + " GROUP BY b.ideleve, a.idecole, e.idclasse,x.id, c.idintermedaireclasse, d.idintermedaireannee, f.idannee "
	                 + " ORDER BY b.ideleve";
	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	        .addValue("idecole", idecole)
	        .addValue("idclasse", idclasse)
	        .addValue("idannee", idannee)
	        .addValue("ideleve", ideleve);
	    
	    try {
		    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
    } catch (Exception e) {
	        e.printStackTrace(); 
	        return Collections.emptyList(); 
	    }
	}
	

	
	public ResponseEntity<?> ElevePar(long idecole, long idclasse, long idannee, long ideleve) {
	    List<EleveModelDto> collections = ElevePars(idecole, idclasse,idannee,ideleve);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}


	public List<EleveModelDto> FicheClasses(long idecole, long idclasse) {
	    String query = "SELECT b.ideleve, UPPER(b.nom) || ' ' || UPPER(b.postnom) || ' ' || UPPER(b.prenom) AS noms, "
	                 + "UPPER(b.sexe) AS sexe, UPPER(b.nomtuteur) AS nomtuteur, b.dateins, b.datenaiss, UPPER(b.email) AS email, "
	                 + "b.telephone, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, UPPER(b.adresse) AS adresse, "
	                 + "c.idintermedaireclasse, d.idintermedaireannee, g.idprovince, UPPER(g.province) AS province,y.id, "
	                 + "h.idcommune, UPPER(h.commune) AS commune, f.idannee, UPPER(f.annee) AS annee, UPPER(a.avenue) AS avenue, UPPER(z.username) AS username, "
	                 + " COALESCE(NULLIF(y.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos, "
	                 + "FROM tab_Eleve b "
	                 + "LEFT JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	                 + "LEFT JOIN tab_Classe e ON c.idclasse = e.idclasse "
	                 + "LEFT JOIN tab_Ecole a ON c.idecole = a.idecole "
	                 + "LEFT JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee "
	                 + "LEFT JOIN tab_Annee f ON d.idannee = f.idannee "
	                 + "LEFT JOIN tab_User z ON z.iduser = b.iduser "
	                 + "LEFT JOIN tab_Province g ON b.idprovince = g.idprovince "
	                 + "LEFT JOIN tab_Commune h ON h.idcommune = a.idcommune "
	                 + "LEFT JOIN tab_Logos y ON y.idecole = a.idecole "
	                 + "WHERE a.idecole = :idecole AND e.idclasse = :idclasse "
	                 + "ORDER BY ideleve, noms ASC";

	   
	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	        .addValue("idecole", idecole)
	        .addValue("idclasse", idclasse);

	 
	  
	    try {
	    	  return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
	    				  } catch (Exception e) {
	        return Collections.emptyList();
	    }
	}


	
	public ResponseEntity<?> FicheClasse(long idecole, long idclasse) throws FileNotFoundException, JRException {
	    try {
	      
	        List<EleveModelDto> collections = FicheClasses(idecole, idclasse);
	        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);
	        
	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("REPORT_DATA_SOURCE", ds);
	        
	        String reportPath = ResourceUtils.getFile("classpath:etats/Ficheleves.jrxml").getAbsolutePath();
	        File reportFile = new File(reportPath);
	        
	        if (!reportFile.exists()) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Report file not found: " + reportPath);
	        }

	        JasperPrint reportlist = JasperFillManager.fillReport(
	            JasperCompileManager.compileReport(reportPath),
	            parameters,
	            ds
	        );

	        String encodedString = Base64.getEncoder().encodeToString(JasperExportManager.exportReportToPdf(reportlist));

	        return ResponseEntity.ok(new reportBase64(encodedString));
	        
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Report file not found: " + e.getMessage());
	    } catch (JRException e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Jasper report error: " + e.getMessage());
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error: " + e.getMessage());
	    }
	}

	 public List<EleveModelDto> CollecteElevedashbords(long idecole,long idclasse,long idannee) {
	        String query = "SELECT UPPER(e.classe) AS classe, UPPER(f.annee) AS annee, COUNT(b.ideleve) AS ideleve "
	        		+ "FROM tab_Eleve b "
	        		+ "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	        		+ "JOIN tab_Classe e ON c.idclasse = e.idclasse "
	        		+ "JOIN tab_Ecole a ON c.idecole = a.idecole "
	        		+ "JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee "
	        		+ "JOIN tab_Annee f ON d.idannee = f.idannee "
	        		+ "JOIN tab_Province g ON b.idprovince = g.idprovince "
	        		+ "JOIN tab_Commune h ON h.idcommune = a.idcommune "
	        		+ "WHERE a.idecole = :idecole AND c.idclasse = :idclasse AND d.idannee = :idannee "
	        		+ "GROUP BY e.classe,f.annee ";
	        MapSqlParameterSource parameters = new MapSqlParameterSource()
	    	        .addValue("idecole", idecole)
	        		.addValue("idclasse", idclasse)
	    	        .addValue("idannee", idannee);
	        return namedParameterJdbcTemplate.query(query,parameters, new BeanPropertyRowMapper<>(EleveModelDto.class));
	    }
	    

	public ResponseEntity<?> CollecteElevedashbord(long idecole,long idclasse,long idannee) {
	    List<EleveModelDto> collections = CollecteElevedashbords(idecole, idclasse,idannee);
	    

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}


	public void logUserIscription(String username, String noms, String classe, String ecole, String annee) {
	    logger.info("Iscription effectué | Élève: {} | Classe: {} | École: {} | Année: {}  | Utilisateur: {}",
	    		noms, classe, ecole, annee,  username );
	}

	public void enregistrerIscription(String username, String noms, String classe, String ecole, String annee) {
	    logUserIscription(username, noms, classe, ecole, annee);
	    System.out.println("✅ Inscription enregistré dans les logs !");
	}
	






}

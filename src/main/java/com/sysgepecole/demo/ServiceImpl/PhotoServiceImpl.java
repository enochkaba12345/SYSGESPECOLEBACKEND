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
import com.sysgepecole.demo.Dto.PhotoModelDto;
import com.sysgepecole.demo.Models.Eleve;
import com.sysgepecole.demo.Models.Photo;
import com.sysgepecole.demo.Models.reportBase64;
import com.sysgepecole.demo.Repository.EleveRepository;
import com.sysgepecole.demo.Repository.PhotoRepository;
import com.sysgepecole.demo.Service.PhotoService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PhotoServiceImpl implements PhotoService{
	
	@Autowired
	private EleveRepository eleverepository;
	
	@Autowired
	private PhotoRepository photorepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static final String UPLOAD_DIR = "F:/uploads/";
	
	@Override
	public Boolean createPhoto(Photo photo) {
	    if (photo == null || photo.getIdeleve() == null) {
	        throw new IllegalArgumentException("Photo ou ID élève ne peut pas être null.");
	    }

	    
	    Optional<Eleve> eleveData = eleverepository.findById(photo.getIdeleve());
	    if (eleveData.isEmpty()) {
	        System.err.println("Élève avec ID " + photo.getIdeleve() + " introuvable.");
	        return false;
	    }

	    
	    Optional<Photo> existingPhoto = photorepository.findByIdeleve(photo.getIdeleve());
	    if (existingPhoto.isPresent()) {
	        Photo photoToUpdate = existingPhoto.get();
	        photoToUpdate.setPhoto(photo.getPhoto());
	        photoToUpdate.setIduser(photo.getIduser());
	        photorepository.save(photoToUpdate);
	    } else {
	        photorepository.save(photo);
	    }

	    return true;
	}


	
	@Override
    public String uploadPhoto(MultipartFile photo) throws IOException {
       
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String filename = System.currentTimeMillis() + "-" + photo.getOriginalFilename();
        File file = new File(UPLOAD_DIR + filename);

        photo.transferTo(file);

        return filename;
    }
	
	 public List<PhotoModelDto> CollectePhotos() {
		 String basePath = "http://localhost:8080/uploads/";
		 
	        String query = "SELECT b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, "
	        		+ " UPPER(b.sexe) AS sexe, UPPER(b.nomtuteur) AS nomtuteur, b.dateins, b.datenaiss, "
	        		+ " UPPER(b.email) AS email, b.telephone, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, "
	        		+ " UPPER(e.classe) AS classe, UPPER(b.adresse) AS adresse, c.idintermedaireclasse, "
	        		+ " d.idintermedaireannee, f.idannee, UPPER(f.annee) AS annee, UPPER(a.avenue) AS avenue, y.id, "
	        		+ " CONCAT('" + basePath + "', CASE WHEN y.photo IS NOT NULL AND y.photo != '' THEN UPPER(y.photo) ELSE 'icon.jpg' END) AS photo"
	        		+ " FROM tab_Eleve b "
	        		+ " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse"
	        		+ " JOIN tab_Classe e ON c.idclasse = e.idclasse "
	        		+ " JOIN tab_Ecole a ON c.idecole = a.idecole "
	        		+ " JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee "
	        		+ " JOIN tab_Annee f ON d.idannee = f.idannee "
	        		+ " JOIN tab_Photo y ON y.ideleve = b.ideleve "
	        		+ " ORDER BY y.id DESC LIMIT 1";
	       
	        try {
	        	 return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(PhotoModelDto.class));
	        	 } catch (Exception e) {
		        e.printStackTrace(); 
		        return Collections.emptyList(); 
		    }
	    }
	    

	public ResponseEntity<?> CollectePhoto() {
	    List<PhotoModelDto> collections = CollectePhotos();
	    

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ces paramètres.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}
	
	 public List<PhotoModelDto> FichePhotos(long id) {
		 String basePath = "http://localhost:8080/uploads/";
		 
	    	String query = "SELECT b.ideleve, UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom,"
	    			+ "UPPER(b.sexe) AS sexe, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe,UPPER(b.adresse) AS adresse,"
	    			+ "c.idintermedaireclasse, d.idintermedaireannee, g.idprovince, UPPER(g.province) AS province, y.id, "
	    			+ " h.idcommune, UPPER(h.commune) AS commune, f.idannee, UPPER(f.annee) AS annee,UPPER(a.avenue) AS avenue,UPPER(z.username) AS username, "
	    			+ " CONCAT('" + basePath + "', CASE WHEN y.photo IS NOT NULL AND y.photo != '' THEN UPPER(y.photo) ELSE 'icon.jpg' END) AS photo"
	    			+ " FROM tab_Eleve b"
	    			+ "	JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse"
	    			+ "	JOIN tab_Classe e ON c.idclasse = e.idclasse"
	    			+ " JOIN tab_Ecole a ON c.idecole = a.idecole"
	    			+ "	JOIN tab_Intermedaireannee d ON b.idintermedaireannee = d.idintermedaireannee"
	    			+ "	JOIN tab_Annee f ON d.idannee = f.idannee"
	    			+ "	JOIN tab_User z ON z.iduser = b.iduser"
	    			+ " JOIN tab_Province g ON b.idprovince = g.idprovince"
	    			+ " JOIN tab_Commune h ON h.idcommune = a.idcommune"
	    			+ " LEFT JOIN tab_Photo y ON y.ideleve = b.ideleve"
	                + " where y.id = :id " 
	                + " ORDER BY y.id limit 1";
	    	 MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);

		    try {
		        return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PhotoModelDto.class));
		    } catch (Exception e) {
		        e.printStackTrace(); 
		        return Collections.emptyList(); 
		    }
		}
	 
		
		public ResponseEntity<?> FichePhoto(long id) {
		    try {
		        List<PhotoModelDto> collections = FichePhotos(id);
		        if (collections.isEmpty()) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                    .body("Aucune fiche élève trouvée pour l'ID : " + id);
		        }
		        File file = ResourceUtils.getFile("classpath:etats/Photo.jrxml");
		        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);
		        Map<String, Object> parameters = new HashMap<>();
		        JasperPrint reportlist = JasperFillManager.fillReport(jasperReport, parameters, ds);
		        byte[] pdfBytes = JasperExportManager.exportReportToPdf(reportlist);
		        if (pdfBytes.length == 0) {
		            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Le rapport est vide.");
		        }
		        
		        String encodedString = Base64.getEncoder().encodeToString(pdfBytes);
		        return ResponseEntity.ok(new reportBase64(encodedString));

		    } catch (FileNotFoundException e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Fichier JRXML introuvable : " + e.getMessage());
		    } catch (JRException e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                .body("Erreur JasperReports : " + e.getMessage());
		    }
		}


}

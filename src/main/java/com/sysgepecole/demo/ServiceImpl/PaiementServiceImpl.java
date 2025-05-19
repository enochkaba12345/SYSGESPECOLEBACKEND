package com.sysgepecole.demo.ServiceImpl;

import java.io.FileNotFoundException;
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

import com.sysgepecole.demo.Dto.EleveModelDto;
import com.sysgepecole.demo.Dto.PaiementDto;
import com.sysgepecole.demo.Models.Frais;
import com.sysgepecole.demo.Models.NumberToWords;
import com.sysgepecole.demo.Models.Paiement;
import com.sysgepecole.demo.Models.reportBase64;
import com.sysgepecole.demo.Repository.EleveRepository;
import com.sysgepecole.demo.Repository.FraisRepository;
import com.sysgepecole.demo.Repository.PaiementRepository;
import com.sysgepecole.demo.Service.PaiementService;

import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.io.IOException;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;





@Service
public class PaiementServiceImpl implements PaiementService{
	
	
    
    private static final Logger logger = LoggerFactory.getLogger(PaiementServiceImpl.class);
	 

	@Autowired
	public PaiementRepository paiementRepository;
	
	@Autowired
	private EleveRepository eleverepository;
	
	@Autowired
	public FraisRepository fraisRepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	@Override
	public Paiement createPaiement(Paiement paiement) {
	    
		 if (paiement.getIduser() == null) {
		        throw new IllegalArgumentException("L'ID utilisateur est requis pour effectuer le paiement.");
		    }
	   
	    List<Paiement> paiements = paiementRepository.findByIdeleveAndIdintermedaireclasse(
	        paiement.getIdeleve(), paiement.getIdintermedaireclasse());
	    
	    
	    List<Frais> fraisList = fraisRepository.findByIntermedaireClasse_IdintermedaireclasseAndIntermedaireAnnee_Idintermedaireannee(paiement.getIdintermedaireclasse(), paiement.getIdintermedaireannee());
	    if (fraisList.isEmpty()) {
	        throw new IllegalStateException("Fee structure not found for class ID: " + paiement.getIdintermedaireclasse() + " and year ID: " + paiement.getIdintermedaireannee());
	    }
	    
	    double totalMontant = fraisList.stream()
	            .mapToDouble(Frais::getMontant)  
	            .sum(); 
	    
	   
	    double totalMontants = paiementRepository.getTotalMontantsByIdeleve(paiement.getIdeleve(), paiement.getIdintermedaireclasse());

	    String telephone = eleverepository.findTelephoneByIdeleve(paiement.getIdeleve());

	    
	    Map<String, Double> trancheMap = Map.of(
		        "1ERE TRANCHE", 40.0,          
		        "2EME TRANCHE", 30.0,        
		        "3EME TRANCHE", 30.0       
		    );
	    
	   
	    Map<String, Double> montantParTranche = new HashMap<>();
	    for (Map.Entry<String, Double> entry : trancheMap.entrySet()) {
	        String tranche = entry.getKey();
	        Double pourcentageTranche = entry.getValue();
	        Double montantTranche = totalMontant * pourcentageTranche / 100.0;
	        montantParTranche.put(tranche, montantTranche);
	    }
	    
	    
	    Map<String, Double> trancheCategoryMap = Map.of(
	        "MINERVAL", 40.0,          
	        "FOURNITURES", 25.0,        
	        "AUTRES FRAIS", 20.0,     
	        "FRAIS TECHNIQUES", 15.0    
	    );
	    
	    
	    Map<String, Map<String, Double>> montantParCategorieParTranche = new HashMap<>();
	    for (Map.Entry<String, Double> entry : montantParTranche.entrySet()) {
	        String tranche = entry.getKey();
	        Double montantTranche = entry.getValue();
	        
	        Map<String, Double> montantParCategorie = new HashMap<>();
	        for (Map.Entry<String, Double> categoryEntry : trancheCategoryMap.entrySet()) {
	            String category = categoryEntry.getKey();
	            Double pourcentageCategorie = categoryEntry.getValue();
	            Double montantCategorie = montantTranche * pourcentageCategorie / 100.0;
	            montantParCategorie.put(category, montantCategorie);
	        }
	        
	        montantParCategorieParTranche.put(tranche, montantParCategorie);
	    }
	    
	   
	    Map<String, Double> dejaPayeParCategorie = new HashMap<>();
	    for (String categorie : trancheCategoryMap.keySet()) {
	        double sommePayee = paiements.stream()
	            .filter(p -> categorie.equals(p.getCategorie()))
	            .mapToDouble(Paiement::getMontants)
	            .sum();
	        dejaPayeParCategorie.put(categorie, sommePayee);
	    }

	    
	    Map<String, Double> resteAPayerParCategorie = new HashMap<>();
	    for (String categorie : trancheCategoryMap.keySet()) {
	        double attendu = montantParCategorieParTranche.values().stream()
	            .mapToDouble(categorieMontants -> categorieMontants.get(categorie))
	            .sum();
	        double deja = dejaPayeParCategorie.getOrDefault(categorie, 0.0);
	        double reste = attendu - deja;
	        resteAPayerParCategorie.put(categorie, reste);
	    }

	   
	    double montantTotalRestant = resteAPayerParCategorie.values().stream()
	        .mapToDouble(Double::doubleValue)
	        .sum();

	    
	    if (paiement.getMontants() == null) {
	        paiement.setMontants(paiement.getMontants()); 
	    }

	    if (paiement.getMontants() > montantTotalRestant) {
	        paiement.setMontants(montantTotalRestant);  
	        paiement.setStatut("SOLDER");  
	    } else if (paiement.getMontants() < montantTotalRestant) {
	        paiement.setStatut("ACOMPTE");  
	    } else {
	        paiement.setStatut("SOLDER");
	    }


	   
	    double montantRestant = paiement.getMontants() != null ? paiement.getMontants() : 0.0;

	    for (Map.Entry<String, Map<String, Double>> trancheEntry : montantParCategorieParTranche.entrySet()) {
	        String trancheKey = trancheEntry.getKey();
	        Map<String, Double> categorieMap = trancheEntry.getValue();

	        for (Map.Entry<String, Double> categorieEntry : categorieMap.entrySet()) {
	            if (montantRestant <= 0) break;

	            String categorie = categorieEntry.getKey();
	            double montantRestantCategorie = resteAPayerParCategorie.getOrDefault(categorie, 0.0);
	            double montantARepartir = Math.min(montantRestant, montantRestantCategorie);

	            if (montantARepartir <= 0) continue;

	            Paiement paiementCategorie = new Paiement();
	            paiementCategorie.setCategorie(categorie);
	            paiementCategorie.setMontants(montantARepartir);
	            paiementCategorie.setIduser(paiement.getIduser());
	            paiementCategorie.setIdeleve(paiement.getIdeleve());
	            paiementCategorie.setIdintermedaireclasse(paiement.getIdintermedaireclasse());
	            paiementCategorie.setIdintermedaireannee(paiement.getIdintermedaireannee());

	           
	           
	           
	            
	            String tranche = formatTrancheLabel(trancheKey);
	            double nouveauSold = totalMontants + montantARepartir;
	            double nouveauSolde = totalMontant - nouveauSold;
	            String statut = (nouveauSolde <= 0) ? "SOLDER" : "ACOMPTE";
	            paiementCategorie.setStatut(statut);

	            String libelleFrais = statut.equals("SOLDER") ? 
	                    "VOUS AVEZ SOLDER TOUT LES FRAIS" : 
	                    "PAIEMENT DE LA " + tranche + " " + categorie + " " + statut;

	            paiementCategorie.setFrais(libelleFrais);

	            paiementRepository.save(paiementCategorie);

	           
	         //   envoyerNotificationWhatsapp(telephone, libelleFrais);

	            montantRestant -= montantARepartir;
	            
	           
	        }

	        if (montantRestant <= 0) break;
	    }
	    return paiement;


	}

	

	private String formatTrancheLabel(String trancheKey) {
	    if (trancheKey == null) return "";
	    trancheKey = trancheKey.trim().toUpperCase();
	    if (trancheKey.contains("1")) return "1ère tranche";
	    if (trancheKey.contains("2")) return "2ème tranche";
	    if (trancheKey.contains("3")) return "3ème tranche";
	    return trancheKey;
	}

 


	 @Override
	 public ResponseEntity<Paiement> updatePaiement(Long idpaiement, Paiement paiement) {
		 try {
	         Optional<Paiement> paiementData = paiementRepository.findById(idpaiement);

	         if (paiementData.isPresent()) {
	             Paiement _paiement = paiementData.get();
	             _paiement.setIdeleve(paiement.getIdeleve());
	             _paiement.setIdintermedaireclasse(paiement.getIdintermedaireclasse());
	             _paiement.setIdpaiement(paiement.getIdpaiement());
	             _paiement.setIdintermedaireannee(paiement.getIdintermedaireannee());
	             _paiement.setFrais(paiement.getFrais());
	             _paiement.setMontants(paiement.getMontants());
	             _paiement.setIduser(paiement.getIduser());

	             return new ResponseEntity<>(paiementRepository.save(_paiement), HttpStatus.OK);
	         } else {
	             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	         }
	     } catch (Exception e) {
	         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	 }
	
	private List<PaiementDto> findByIdeleves(long ideleve,long idintermedaireclasse) {
		 String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, UPPER(b.sexe) AS sexe, "
                 + "a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, "
                 + "c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, UPPER(f.annee) AS annee, UPPER(a.avenue) AS avenue "
                 + "FROM tab_Eleve b "
                 + "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
                 + "JOIN tab_Classe e ON c.idclasse = e.idclasse "
                 + "JOIN tab_Ecole a ON c.idecole = a.idecole "
                 + "JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
                 + "JOIN tab_Annee f ON d1.idannee = f.idannee "
                 + "JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
                 + "WHERE b.ideleve = :ideleve AND c.idintermedaireclasse = :idintermedaireclasse "
                 + "GROUP BY b.ideleve, a.idecole, e.idclasse, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee "
                 + "ORDER BY b.ideleve";
    MapSqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("ideleve", ideleve)
        .addValue("idintermedaireclasse", idintermedaireclasse);
    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}
	public ResponseEntity<?> findByIdeleve(long ideleve,long idintermedaireclasse) {
	    List<PaiementDto> collections = findByIdeleves(ideleve,idintermedaireclasse);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ce nom.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}


	
	public List<PaiementDto> CollecteFraisPayerEleves(long ideleve, long idtranche, long idcategorie) {
	    String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, "
	    		+ "a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, "
	    		+ "c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, UPPER(k.categorie) AS categorie, "
	    		+ "UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, d2.montant, n.montants, b.telephone "
	    		+ " FROM tab_Eleve b "
	    		+ " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	    		+ " JOIN tab_Classe e ON c.idclasse = e.idclasse "
	    		+ " JOIN tab_Ecole a ON c.idecole = a.idecole "
	    		+ " JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
	    		+ " JOIN tab_Annee f ON d1.idannee = f.idannee "
	    		+ " JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
	    		+ " JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
	    		+ " JOIN tab_Tranche l ON l.idtranche = d2.idtranche "
	    		+ " JOIN tab_Paiement n ON n.ideleve = b.ideleve   "
	    		+ " WHERE b.ideleve = :ideleve AND l.idtranche = :idtranche AND k.idcategorie = :idcategorie "
	    		+ " GROUP BY b.ideleve, a.idecole, e.idclasse, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, l.idtranche, n.idpaiement,d2.idfrais "
	    		+ " ORDER BY b.ideleve ";
	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	        .addValue("ideleve", ideleve)
	        .addValue("idtranche", idtranche)
	        .addValue("idcategorie", idcategorie);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}

	public ResponseEntity<?> CollecteFraisPayerEleve(long ideleve, long idtranche, long idcategorie) {
	    List<PaiementDto> collections = CollecteFraisPayerEleves(ideleve, idtranche, idcategorie);
	   


	    if (collections.isEmpty()) {
	       
	        return ResponseEntity.ok(collections);
	    } else {
	       
	            return ResponseEntity.ok(collections);
	    }
	}


	public List<PaiementDto> CollectionPaiements(Long ideleve) {
		 String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, "
			 		+ " a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, n.idpaiement, "
			 		+ " c.idintermedaireclasse, d1.idintermedaireannee, f.idannee,  UPPER(n.categorie) AS categorie, "
			 		+ " UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.datepaie, UPPER(n.frais) as frais, "
			 		+ " m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune, n.montants, "
			 		+ " (SELECT SUM(d2_sub.montant) "
			 		+ " FROM tab_Frais d2_sub "
			 		+ " WHERE d2_sub.idintermedaireclasse = c.idintermedaireclasse "
			 		+ " AND d2_sub.idintermedaireannee = d1.idintermedaireannee) AS montant_frais, "
			 		+ " (SELECT SUM(n_sub.montants) "
			 		+ " FROM tab_Paiement n_sub "
			 		+ " WHERE n_sub.ideleve = b.ideleve) AS montant_paiement"
			 		+ " FROM tab_Eleve b "
			 		+ " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
			 		+ " JOIN tab_Classe e ON c.idclasse = e.idclasse "
			 		+ " JOIN tab_Ecole a ON c.idecole = a.idecole "
			 		+ " JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
			 		+ " JOIN tab_Annee f ON d1.idannee = f.idannee "
			 		+ " JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
			 		+ " JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
			 		+ " JOIN tab_Tranche l ON l.idtranche = d2.idtranche "
			 		+ " JOIN tab_Province m ON m.idprovince = b.idprovince "
			 		+ " JOIN tab_Commune h ON h.idcommune = a.idcommune "
			 		+ " JOIN tab_Paiement n ON n.ideleve = b.ideleve "
			 		+ " WHERE b.ideleve = :ideleve "
			 		+ " GROUP BY b.ideleve, a.idecole, e.idclasse, h.idcommune, m.idprovince, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais "
			 		+ " ORDER BY n.idpaiement DESC LIMIT 1";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ideleve", ideleve);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}

	public ResponseEntity<?> CollectionPaiement(Long ideleve) {
		List<PaiementDto> collections = CollectionPaiements(ideleve);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé pour cet élève.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
		
	}

	public List<PaiementDto> ImpressionRecuEleveAcomptes(long ideleve) {
	    String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, "
	                 + "a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, n.idpaiement, "
	                 + "c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, UPPER(k.categorie) AS categorie, "
	                 + "UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.montants,n.datepaie, UPPER(n.frais) as frais, "
	                 + "m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune,UPPER(z.username) AS username,z.iduser, "
		         + " COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos,x.id "
	                 + "FROM tab_Eleve b "
	                 + "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	                 + "JOIN tab_Classe e ON c.idclasse = e.idclasse "
	                 + "JOIN tab_Ecole a ON c.idecole = a.idecole "
	                 + "JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
	                 + "JOIN tab_Annee f ON d1.idannee = f.idannee "
	                 + "JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
	                 + "JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
	                 + "JOIN tab_Tranche l ON l.idtranche = d2.idtranche "
	                 + "JOIN tab_Province m ON m.idprovince = b.idprovince "
	                 + "JOIN tab_Commune h ON h.idcommune = a.idcommune "
	                 + "JOIN tab_Paiement n ON n.ideleve = b.ideleve  "
	                 + "JOIN tab_User z ON z.iduser = n.iduser "
	                 + " LEFT JOIN tab_Logos x ON x.idecole = a.idecole " 
	                 + "WHERE b.ideleve = :ideleve "
	                 + "GROUP BY b.ideleve, a.idecole,x.id, e.idclasse, h.idcommune, m.idprovince,z.iduser,c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais "
	                 + "ORDER BY n.idpaiement DESC LIMIT 1";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ideleve", ideleve);
	   
	    try {
	    	 return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	    				  } catch (Exception e) {
	        return Collections.emptyList();
	    }
	
	}

	public ResponseEntity<?> ImpressionRecuEleveAcompte(long ideleve) {
    try {
         List<PaiementDto> collections = ImpressionRecuEleveAcomptes(ideleve);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucune fiche élève trouvée pour l'ID : " + ideleve);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);

	     Map<String, Object> parameters = new HashMap<>();
	        parameters.put("NumberToWords", new NumberToWords()); 
        InputStream jrxmlStream = new ClassPathResource("etats/Recu.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        JasperPrint reportlist = JasperFillManager.fillReport(jasperReport, new HashMap<>(), ds);

        String encodedString = Base64.getEncoder()
                .encodeToString(JasperExportManager.exportReportToPdf(reportlist));

        return ResponseEntity.ok(new reportBase64(encodedString));

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fichier JRXML introuvable ou inaccessible : " + e.getMessage());
    } catch (JRException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur JasperReports : " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur inattendue : " + e.getMessage());
    }
}
		
	
	public List<PaiementDto> ImpressionRecuEleveSoldes(long ideleve) {
	    String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, " 
	    		+ " a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, "
	    		+ " n.idpaiement, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, UPPER(n.categorie) AS categorie, "
	    		+ " UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.datepaie, UPPER(n.frais) AS frais, "
	    		+ " m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune,UPPER(z.username) AS username,z.iduser, "
	    	        + " COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos,x.id "
	    		+ " (SELECT SUM(d2_sub.montant) "
	    		+ " FROM tab_Frais d2_sub " 
	    		+ " WHERE d2_sub.idintermedaireclasse = c.idintermedaireclasse AND d2_sub.idintermedaireannee = d1.idintermedaireannee) AS montant_frais, "
	    		+ " (SELECT SUM(n_sub.montants) "
	    		+ " FROM tab_Paiement n_sub "
	    		+ " WHERE n_sub.ideleve = b.ideleve) AS montant_paiement, "
	    		+ " CASE "
	    		+ " WHEN "
	    		+ " (SELECT SUM(d2_sub.montant) "
	    		+ " FROM tab_Frais d2_sub "
	    		+ " WHERE d2_sub.idintermedaireclasse = c.idintermedaireclasse AND d2_sub.idintermedaireannee = d1.idintermedaireannee) = "
	    		+ " (SELECT SUM(n_sub.montants) "
	    		+ " FROM tab_Paiement n_sub "
	    		+ " WHERE n_sub.ideleve = b.ideleve) "
	    		+ " THEN "
	    		+ " (SELECT SUM(n_sub.montants) "
	    		+ " FROM tab_Paiement n_sub "
	    		+ " WHERE n_sub.ideleve = b.ideleve) "
	    		+ " ELSE 0 "
	    		+ " END AS montants " 
	    		+ " FROM tab_Eleve b "
	    		+ " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	    		+ " JOIN tab_Classe e ON c.idclasse = e.idclasse "
	    		+ " JOIN tab_Ecole a ON c.idecole = a.idecole "
	    		+ " JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
	    		+ " JOIN tab_Annee f ON d1.idannee = f.idannee "
	    		+ " JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
	    		+ " JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
	    		+ " JOIN tab_Tranche l ON l.idtranche = d2.idtranche "
	    		+ " JOIN tab_Province m ON m.idprovince = b.idprovince "
	    		+ " JOIN tab_Commune h ON h.idcommune = a.idcommune "
	    		+ " JOIN tab_Paiement n ON n.ideleve = b.ideleve "
	    		+ " JOIN tab_User z ON z.iduser = n.iduser "
	    		+ " LEFT JOIN tab_Logos x ON x.idecole = a.idecole " 
	    		+ " WHERE b.ideleve = :ideleve "
	    		+ " GROUP BY b.ideleve, a.idecole, e.idclasse, h.idcommune, m.idprovince, "
	    		+ " c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, "
	    		+ " k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais,z.iduser,x.id "
	    		+ " ORDER BY n.idpaiement DESC LIMIT 1 ";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ideleve", ideleve);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}


	public ResponseEntity<?> ImpressionRecuEleveSolde(long idpaiement) {
    try {
         List<PaiementDto> collections =  ImpressionRecuEleveSoldes(idpaiement);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucune fiche élève trouvée pour l'ID : " + idpaiement);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);

	     Map<String, Object> parameters = new HashMap<>();
	        parameters.put("NumberToWords", new NumberToWords()); 
        InputStream jrxmlStream = new ClassPathResource("etats/Recu.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        JasperPrint reportlist = JasperFillManager.fillReport(jasperReport, new HashMap<>(), ds);

        String encodedString = Base64.getEncoder()
                .encodeToString(JasperExportManager.exportReportToPdf(reportlist));

        return ResponseEntity.ok(new reportBase64(encodedString));

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fichier JRXML introuvable ou inaccessible : " + e.getMessage());
    } catch (JRException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur JasperReports : " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur inattendue : " + e.getMessage());
    }
}
		
	
	public List<PaiementDto> ImpressionRecuModeEleveSoldes(long idpaiement) {
	    String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, "
	    		+ "	a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, "
	    		+ "	n.idpaiement, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, UPPER(n.categorie) AS categorie, "
	    		+ "	UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.datepaie, UPPER(n.frais) AS frais, "
	    		+ "	m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune,UPPER(z.username) AS username,z.iduser, "
	    		+ " COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos,x.id "
	    		+ "	(SELECT SUM(d2_sub.montant) "
	    		+ "	FROM tab_Frais d2_sub "
	    		+ "	WHERE d2_sub.idintermedaireclasse = c.idintermedaireclasse AND d2_sub.idintermedaireannee = d1.idintermedaireannee) AS montant_frais, "
	    		+ "	(SELECT SUM(n_sub.montants) "
	    		+ "	FROM tab_Paiement n_sub "
	    		+ "	WHERE n_sub.ideleve = b.ideleve) AS montant_paiement, "
	    		+ "	CASE "
	    		+ "	WHEN "
	    		+ "	(SELECT SUM(d2_sub.montant) "
	    		+ "	FROM tab_Frais d2_sub "
	    		+ "	WHERE d2_sub.idintermedaireclasse = c.idintermedaireclasse AND d2_sub.idintermedaireannee = d1.idintermedaireannee) = "
	    		+ "	(SELECT SUM(n_sub.montants) "
	    		+ "	FROM tab_Paiement n_sub "
	    		+ "	WHERE n_sub.ideleve = b.ideleve) "
	    		+ "	THEN "
	    		+ "	(SELECT SUM(n_sub.montants) "
	    		+ "	FROM tab_Paiement n_sub "
	    		+ "	WHERE n_sub.ideleve = b.ideleve) "
	    		+ "	ELSE 0 "
	    		+ "	END AS montants "
	    		+ "	FROM tab_Eleve b "
	    		+ "	JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	    		+ "	JOIN tab_Classe e ON c.idclasse = e.idclasse "
	    		+ "	JOIN tab_Ecole a ON c.idecole = a.idecole "
	    		+ "	JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
	    		+ "	JOIN tab_Annee f ON d1.idannee = f.idannee "
	    		+ "	JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
	    		+ "	JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
	    		+ "	JOIN tab_Tranche l ON l.idtranche = d2.idtranche "
	    		+ " JOIN tab_Province m ON m.idprovince = b.idprovince "
	    		+ " JOIN tab_Commune h ON h.idcommune = a.idcommune "
	    		+ "	JOIN tab_Paiement n ON n.ideleve = b.ideleve "
	    		+ "	JOIN tab_User z ON z.iduser = n.iduser "
	    		+ " LEFT JOIN tab_Logos x ON x.idecole = a.idecole " 
	    		+ "	WHERE n.idpaiement = :idpaiement "
	    		+ "	GROUP BY b.ideleve, a.idecole, e.idclasse, h.idcommune, m.idprovince, "
	    		+ "	c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, "
	    		+ " k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais,z.iduser,x.id "
	    		+ "	ORDER BY n.idpaiement DESC LIMIT 1 ";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("idpaiement", idpaiement);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}


		public ResponseEntity<?> ImpressionRecuModeEleveSolde(long idpaiement) {
    try {
         List<PaiementDto> collections = ImpressionRecuModeEleveSoldes(idpaiement);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucune fiche élève trouvée pour l'ID : " + idpaiement);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);

	     Map<String, Object> parameters = new HashMap<>();
	        parameters.put("NumberToWords", new NumberToWords()); 
        InputStream jrxmlStream = new ClassPathResource("etats/Recu.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        JasperPrint reportlist = JasperFillManager.fillReport(jasperReport, new HashMap<>(), ds);

        String encodedString = Base64.getEncoder()
                .encodeToString(JasperExportManager.exportReportToPdf(reportlist));

        return ResponseEntity.ok(new reportBase64(encodedString));

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fichier JRXML introuvable ou inaccessible : " + e.getMessage());
    } catch (JRException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur JasperReports : " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur inattendue : " + e.getMessage());
    }
}
		
	public List<PaiementDto> ImpressionRecuModeEleveAcomptes(long idpaiement) {
	    String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, "
	                 + "a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, n.idpaiement, "
	                 + "c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, UPPER(k.categorie) AS categorie, "
	                 + "UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.montants,n.datepaie, UPPER(n.frais) as frais, "
	                 + "m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune,UPPER(z.username) AS username,z.iduser,x.id, "
	                  + " COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos "
	                 + "FROM tab_Eleve b "
	                 + "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	                 + "JOIN tab_Classe e ON c.idclasse = e.idclasse "
	                 + "JOIN tab_Ecole a ON c.idecole = a.idecole "
	                 + "JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
	                 + "JOIN tab_Annee f ON d1.idannee = f.idannee "
	                 + "JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
	                 + "JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
	                 + "JOIN tab_Tranche l ON l.idtranche = d2.idtranche "
	                 + "JOIN tab_Province m ON m.idprovince = b.idprovince "
	                 + "JOIN tab_Commune h ON h.idcommune = a.idcommune "
	                 + "JOIN tab_Paiement n ON n.ideleve = b.ideleve  "
	                 + "JOIN tab_User z ON z.iduser = n.iduser "
	                 + " LEFT JOIN tab_Logos x ON x.idecole = a.idecole " 
	                 + "WHERE n.idpaiement = :idpaiement "
	                 + "GROUP BY b.ideleve, a.idecole, e.idclasse,x.id, h.idcommune, m.idprovince,z.iduser, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais "
	                 + "ORDER BY n.idpaiement LIMIT 1";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("idpaiement", idpaiement);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}

	
		public ResponseEntity<?> ImpressionRecuModeEleveAcompte(long idpaiement) {
    try {
         List<PaiementDto> collections =  ImpressionRecuModeEleveAcomptes(idpaiement);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucune fiche élève trouvée pour l'ID : " + idpaiement);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);

	     Map<String, Object> parameters = new HashMap<>();
	        parameters.put("NumberToWords", new NumberToWords()); 
        InputStream jrxmlStream = new ClassPathResource("etats/Recu.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        JasperPrint reportlist = JasperFillManager.fillReport(jasperReport, new HashMap<>(), ds);

        String encodedString = Base64.getEncoder()
                .encodeToString(JasperExportManager.exportReportToPdf(reportlist));

        return ResponseEntity.ok(new reportBase64(encodedString));

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fichier JRXML introuvable ou inaccessible : " + e.getMessage());
    } catch (JRException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur JasperReports : " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur inattendue : " + e.getMessage());
    }
}
		

	
	public List<PaiementDto> ImpressionRecuModeEleves(long idpaiement) {
		
   String query = """
           SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, 
	                 a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, n.idpaiement, 
	                 c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, UPPER(k.categorie) AS categorie, 
	                 UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.montants,n.datepaie, UPPER(n.frais) as frais, 
	                 m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune,UPPER(z.username) AS username,z.iduser,x.id, 
	                  COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos 
	                 FROM tab_Eleve b 
	                 JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse 
	                 JOIN tab_Classe e ON c.idclasse = e.idclasse 
	                 JOIN tab_Ecole a ON c.idecole = a.idecole 
	                 JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee 
	                 JOIN tab_Annee f ON d1.idannee = f.idannee 
	                 JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse 
	                 JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie 
	                 JOIN tab_Tranche l ON l.idtranche = d2.idtranche 
	                 JOIN tab_Province m ON m.idprovince = b.idprovince 
	                 JOIN tab_Commune h ON h.idcommune = a.idcommune 
	                 JOIN tab_Paiement n ON n.ideleve = b.ideleve  
	                 JOIN tab_User z ON z.iduser = n.iduser 
	                 LEFT JOIN tab_Logos x ON x.idecole = a.idecole 
	                 WHERE n.idpaiement = :idpaiement 
	                 GROUP BY b.ideleve, a.idecole, e.idclasse,x.id, h.idcommune, m.idprovince,z.iduser, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais 
	                 ORDER BY n.idpaiement LIMIT 1
        """;
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("idpaiement", idpaiement);
	    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	}

	public ResponseEntity<?> ImpressionRecuModeEleve(long idpaiement) {
    try {
        List<PaiementDto> collections = ImpressionRecuModeEleves(idpaiement);

        if (collections.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucune fiche élève trouvée pour l'ID : " + idpaiement);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);
	     Map<String, Object> parameters = new HashMap<>();
	        parameters.put("NumberToWords", new NumberToWords()); 

        InputStream jrxmlStream = new ClassPathResource("etats/Recu.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        JasperPrint reportlist = JasperFillManager.fillReport(jasperReport, new HashMap<>(), ds);

        String encodedString = Base64.getEncoder()
                .encodeToString(JasperExportManager.exportReportToPdf(reportlist));

        return ResponseEntity.ok(new reportBase64(encodedString));

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fichier JRXML introuvable ou inaccessible : " + e.getMessage());
    } catch (JRException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur JasperReports : " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur inattendue : " + e.getMessage());
    }
}
	


	public List<PaiementDto> searchPaiements(String nom, Long idecole, boolean isAdmin) {

	    StringBuilder query = new StringBuilder(
	        "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, UPPER(b.sexe) AS sexe, " +
	        "b.ideleve, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, SUM(n.montants) AS montants, " +
	        "MAX(n.datepaie) AS datepaie, f.idannee, x.id, " +
		" COALESCE(NULLIF(x.photo, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747291830/icon_jygejr.jpg') AS photo, " +
	        "UPPER(f.annee) AS annee, " +
	        "(SELECT UPPER(n2.frais) FROM tab_Paiement n2 WHERE n2.ideleve = b.ideleve ORDER BY n2.datepaie DESC LIMIT 1) AS frais " +
	        "FROM tab_Eleve b " +
	        "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse " +
	        "JOIN tab_Classe e ON c.idclasse = e.idclasse " +
	        "JOIN tab_Ecole a ON c.idecole = a.idecole " +
	        "JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee " +
	        "JOIN tab_Annee f ON d1.idannee = f.idannee " +
	        "JOIN tab_Paiement n ON n.ideleve = b.ideleve " +
	        "LEFT JOIN tab_Photo x ON x.ideleve = b.ideleve " +
	        "WHERE LOWER(b.nom) LIKE :nom "
	    );

	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	        .addValue("nom", "%" + nom.toLowerCase().trim() + "%");

	    if (!isAdmin) {
	        query.append("AND a.idecole = :idecole ");
	        parameters.addValue("idecole", idecole);
	    }

	    query.append(
	        "GROUP BY b.ideleve, b.nom, b.postnom, b.prenom, b.sexe, a.idecole, e.idclasse, e.classe, " +
	        "c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, f.annee, x.id, x.photo " +
	        "ORDER BY montants;"
	    );

	    try {
	        return namedParameterJdbcTemplate.query(query.toString(), parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}


	public ResponseEntity<?> searchPaiements(String userRole, String nom, Long idecole) {
	    boolean isAdmin = "ADMIN".equalsIgnoreCase(userRole);
	    
	    List<PaiementDto> collections = searchPaiements(nom, idecole, isAdmin);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé.");
	    }
	    return ResponseEntity.ok(collections);
	}



	public List<PaiementDto> PaiementDeleves(Long ideleve) {
	    String query = " SELECT DISTINCT ON (n.idpaiement) " +
	                   " UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, UPPER(b.sexe) AS sexe, b.ideleve, " +
	                   " a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, n.idpaiement, " +
	                   " c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, UPPER(k.categorie) AS categorie, " +
	                   " UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.montants, n.datepaie, UPPER(n.frais) AS frais, " +
	                   " UPPER(z.username) AS username,z.iduser, x.id, " +
		           " COALESCE(NULLIF(x.photo, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747291830/icon_jygejr.jpg') AS photo " + 
	                   " FROM tab_Eleve b " +
	                   " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse " +
	                   " JOIN tab_Classe e ON c.idclasse = e.idclasse " +
	                   " JOIN tab_Ecole a ON c.idecole = a.idecole " +
	                   " JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee " +
	                   " JOIN tab_Annee f ON d1.idannee = f.idannee " +
	                   " JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse " +
	                   " JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie " +
	                   " JOIN tab_Tranche l ON l.idtranche = d2.idtranche " +
	                   " JOIN tab_Paiement n ON n.ideleve = b.ideleve " +
	                   " LEFT JOIN tab_Photo x ON x.ideleve = b.ideleve " +
	                   " JOIN tab_User z ON z.iduser = n.iduser " +
	                   " WHERE b.ideleve = :ideleve " +
	                   " ORDER BY n.idpaiement DESC";

	    MapSqlParameterSource parameters = new MapSqlParameterSource()
		        .addValue("ideleve", ideleve);

	    try {
	        return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return Collections.emptyList(); 
	    }
	}

	public ResponseEntity<?> PaiementDeleve(Long ideleve) {
	    List<PaiementDto> collections = PaiementDeleves(ideleve);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé pour cet élève.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
	}

	public List<PaiementDto> CollectionPaiementModes(Long idpaiement) {
		 String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, "
                + "a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, n.idpaiement, "
                + "c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, UPPER(k.categorie) AS categorie, "
                + "UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.montants,n.datepaie, UPPER(n.frais) as frais, "
		+ " COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos, x.id, "
                + "m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune,UPPER(z.username) AS username,z.iduser "
                + "FROM tab_Eleve b "
                + "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
                + "JOIN tab_Classe e ON c.idclasse = e.idclasse "
                + "JOIN tab_Ecole a ON c.idecole = a.idecole "
                + "JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
                + "JOIN tab_Annee f ON d1.idannee = f.idannee "
                + "JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
                + "JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
                + "JOIN tab_Tranche l ON l.idtranche = d2.idtranche "
                + "JOIN tab_Province m ON m.idprovince = b.idprovince "
                + " JOIN tab_Commune h ON h.idcommune = a.idcommune "
                + "JOIN tab_Paiement n ON n.ideleve = b.ideleve  "
                + " LEFT JOIN tab_Logos x ON x.idecole = a.idecole " 
                + "JOIN tab_User z ON z.iduser = n.iduser "
                + "WHERE n.idpaiement = :idpaiement "
                + "GROUP BY b.ideleve, a.idecole, x.id, e.idclasse,z.iduser, h.idcommune, m.idprovince, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais "
                + "ORDER BY n.idpaiement limit 1";
	    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("idpaiement", idpaiement);
	   try {
		   return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
		     } catch (Exception e) {
	        e.printStackTrace(); 
	        return Collections.emptyList(); 
	    }
	}

	public ResponseEntity<?> CollectionPaiementMode(Long idpaiement) {
		List<PaiementDto> collections = CollectionPaiementModes(idpaiement);

	    if (collections.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé pour cet élève.");
	    } else {
	        return ResponseEntity.ok(collections);
	    }
		
	}

	public List<PaiementDto> EcoleParClasses(Long idecole, Long idclasse, Long idannee) {

	    String query =
	        "WITH PaiementsDistincts AS ( " +
	        "   SELECT n.ideleve, SUM(n.montants) AS montants " +
	        "   FROM tab_Paiement n " +
	        "   JOIN tab_Eleve b ON n.ideleve = b.ideleve " +
	        "   JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse " +
	        "   JOIN tab_Intermedaireannee ia ON b.idintermedaireannee = ia.idintermedaireannee " +
	        "   WHERE c.idecole = :idecole AND c.idclasse = :idclasse AND ia.idannee = :idannee " +
	        "   GROUP BY n.ideleve " +
	        "), " +
	        "MontantsFrais AS ( " +
	        "   SELECT d2.idintermedaireclasse, SUM(d2.montant) AS montant " +
	        "   FROM tab_Frais d2 " +
	        "   JOIN tab_Intermedaireclasse c ON d2.idintermedaireclasse = c.idintermedaireclasse " +
	        "   JOIN tab_Intermedaireannee ia ON d2.idintermedaireannee = ia.idintermedaireannee " +
	        "   WHERE c.idecole = :idecole AND c.idclasse = :idclasse AND ia.idannee = :idannee " +
	        "   GROUP BY d2.idintermedaireclasse " +
	        ") " +
	        "SELECT " +
	        "   UPPER(b.nom) AS nom, " +
	        "   UPPER(b.postnom) AS postnom, " +
	        "   UPPER(b.prenom) AS prenom, " +
	        "   b.ideleve, " +
	        "   UPPER(b.sexe) AS sexe, " +
	        "   UPPER(a.ecole) AS ecole, " +
	        "   e.idclasse, " +
	        "   UPPER(e.classe) AS classe, " +
	        "   an.annee AS annee, " +
	        "   COALESCE(pd.montants, 0) AS montants, " +
	        "   COALESCE(mf.montant, 0) AS montant, " +
	        "   (COALESCE(mf.montant, 0) - COALESCE(pd.montants, 0)) AS reste, " +
	        " COALESCE(NULLIF(x.photo, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747291830/icon_jygejr.jpg') AS photo " + 
	        "FROM tab_Eleve b " +
	        "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse " +
	        "JOIN tab_Intermedaireannee ia ON b.idintermedaireannee = ia.idintermedaireannee " +
	        "JOIN tab_Classe e ON c.idclasse = e.idclasse " +
	        "JOIN tab_Ecole a ON c.idecole = a.idecole " +
	        "JOIN tab_Annee an ON ia.idannee = an.idannee " +
	        "LEFT JOIN PaiementsDistincts pd ON b.ideleve = pd.ideleve " +
	        "LEFT JOIN MontantsFrais mf ON c.idintermedaireclasse = mf.idintermedaireclasse " +
	        "LEFT JOIN tab_Photo x ON x.ideleve = b.ideleve " +
	        "WHERE c.idecole = :idecole AND c.idclasse = :idclasse AND ia.idannee = :idannee " +
	        "ORDER BY b.nom, b.postnom, b.prenom;";

	    MapSqlParameterSource parameters = new MapSqlParameterSource()
	        .addValue("idecole", idecole)
	        .addValue("idclasse", idclasse)
	        .addValue("idannee", idannee);

	    try {
	        return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}



public ResponseEntity<?> EcoleParClasse(Long idecole,Long idclasse,Long idannee) {
	List<PaiementDto> collections = EcoleParClasses(idecole,idclasse,idannee);

    if (collections.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé pour cet élève.");
    } else {
        return ResponseEntity.ok(collections);
    }
	
}

public List<PaiementDto> FichePaiementeleves(Long ideleve) {
	 String query = " SELECT DISTINCT ON (n.idpaiement) "+
    " UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, UPPER(b.sexe) AS sexe , UPPER(b.adresse) AS adresse, b.ideleve, "+ 
    " a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, n.idpaiement, "+ 
    " c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, UPPER(k.categorie) AS categorie, "+ 
    " UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.montants, n.datepaie, UPPER(n.frais) AS frais, "+ 
    " UPPER(v.province) AS province, v.idprovince, UPPER(m.commune) AS commune, m.idcommune,x.id, " +
   " COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos " +
    " FROM tab_Eleve b "+
    " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "+
    " JOIN tab_Classe e ON c.idclasse = e.idclasse "+
    " JOIN tab_Ecole a ON c.idecole = a.idecole "+
    " JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "+
    " JOIN tab_Annee f ON d1.idannee = f.idannee "+
    " JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "+
    " JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "+
    " JOIN tab_Tranche l ON l.idtranche = d2.idtranche "+
    " JOIN tab_Province v ON v.idprovince = b.idprovince "+
    " JOIN tab_Commune m ON m.idcommune = a.idcommune "+
    " JOIN tab_Paiement n ON n.ideleve = b.ideleve  "+
    " LEFT JOIN tab_Logos x ON x.idecole = a.idecole " +
    " WHERE b.ideleve = :ideleve " + 
    " ORDER BY n.idpaiement DESC ";
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ideleve", ideleve);
    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
}


@Override
public ResponseEntity<?> FichePaiementeleve(Long ideleve) throws FileNotFoundException, JRException {
	 try {
	        List<PaiementDto> collections = FichePaiementeleves(ideleve);
	        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);
	        
	        Map<String, Object> parameters = new HashMap<>();
	        parameters.put("REPORT_DATA_SOURCE", ds);

	        JasperPrint reportlist = JasperFillManager.fillReport(
	            JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:etats/Fichepaiement.jrxml").getAbsolutePath()), parameters);

	        String encodedString = Base64.getEncoder().encodeToString(JasperExportManager.exportReportToPdf(reportlist));
	        return ResponseEntity.ok(new reportBase64(encodedString));
	    } catch (FileNotFoundException e) {
	        return ResponseEntity.ok().body(e.getMessage());
	    } catch (JRException e) {
	        return ResponseEntity.ok().body(e.getMessage());
	    }
}




public List<PaiementDto> FicheRecouvrementClasses(long idecole, long idclasse, long idannee) {
    String query ="WITH PaiementsDistincts AS ( " +
        "    SELECT n.ideleve, SUM(n.montants) AS montants " +
        "    FROM tab_Paiement n " +
        "    JOIN tab_Eleve b ON n.ideleve = b.ideleve " +
        "    JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse " +
        "    JOIN tab_Intermedaireannee ia ON b.idintermedaireannee = ia.idintermedaireannee " +
        "    WHERE c.idecole = :idecole AND c.idclasse = :idclasse AND ia.idannee = :idannee " +
        "    GROUP BY n.ideleve " +
        "), " +
        "MontantsFrais AS ( " +
        "    SELECT d2.idintermedaireclasse, SUM(d2.montant) AS montant " +
        "    FROM tab_Frais d2 " +
        "    JOIN tab_Intermedaireclasse c ON d2.idintermedaireclasse = c.idintermedaireclasse " +
        "    JOIN tab_Intermedaireannee ia ON d2.idintermedaireannee = ia.idintermedaireannee " +
        "    WHERE c.idecole = :idecole AND c.idclasse = :idclasse AND ia.idannee = :idannee " +
        "    GROUP BY d2.idintermedaireclasse " +
        ") " +
        "SELECT " +
        "    UPPER(b.nom || ' ' || b.postnom || ' ' || b.prenom) AS noms, " +
        "    UPPER(b.sexe) AS sexe, " +
        "    UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, " +
        "    e.idclasse, UPPER(e.classe) AS classe, UPPER(an.annee) AS annee_scolaire, " +
        "    UPPER(h.commune) AS commune, UPPER(g.province) AS province, " +
	" COALESCE(NULLIF(x.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos, " +
        "    COALESCE(pd.montants, 0) AS montant_paiement, " +
        "    COALESCE(mf.montant, 0) AS montant_attendu, " +
        "    (COALESCE(mf.montant, 0) - COALESCE(pd.montants, 0)) AS montant_frais " +
        "FROM tab_Eleve b " +
        "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse " +
        "JOIN tab_Intermedaireannee ia ON b.idintermedaireannee = ia.idintermedaireannee " +
        "JOIN tab_Classe e ON c.idclasse = e.idclasse " +
        "JOIN tab_Ecole a ON c.idecole = a.idecole " +
        "JOIN tab_Annee an ON ia.idannee = an.idannee " +
        "LEFT JOIN PaiementsDistincts pd ON b.ideleve = pd.ideleve " +
        "LEFT JOIN MontantsFrais mf ON c.idintermedaireclasse = mf.idintermedaireclasse " +
        "LEFT JOIN tab_Province g ON a.idprovince = g.idprovince " +
        "LEFT JOIN tab_Commune h ON a.idcommune = h.idcommune " +
        "LEFT JOIN tab_Logos x ON x.idecole = a.idecole " +
        "WHERE c.idecole = :idecole AND c.idclasse = :idclasse AND ia.idannee = :idannee " +
        "ORDER BY noms";

    MapSqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("idecole", idecole)
        .addValue("idclasse", idclasse)
        .addValue("idannee", idannee);

    try {
    	  return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
    	      } catch (Exception e) {
        e.printStackTrace();
        return Collections.emptyList();
    }
}


	@Override
public ResponseEntity<?> FicheRecouvrementClasse(long idecole, long idclasse, long idannee) throws FileNotFoundException, JRException {
    try {
           List<PaiementDto> collections = FicheRecouvrementClasses(idecole,idclasse,idannee);

        

       JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(collections);

        InputStream jrxmlStream = new ClassPathResource("etats/FicheRecouvrement.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
        JasperPrint reportlist = JasperFillManager.fillReport(jasperReport, new HashMap<>(), ds);

        String encodedString = Base64.getEncoder()
                .encodeToString(JasperExportManager.exportReportToPdf(reportlist));

        return ResponseEntity.ok(new reportBase64(encodedString));

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Fichier JRXML introuvable ou inaccessible : " + e.getMessage());
    } catch (JRException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur JasperReports : " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur inattendue : " + e.getMessage());
    }
}
	

public List<PaiementDto> CollectionPaiementse(long idecole) {
	 String query = "SELECT UPPER(cl.classe) AS classe, UPPER(an.annee) AS annee, SUM(p.montants) AS montants "
	 		+ " FROM tab_Paiement p "
	 		+ " JOIN tab_Eleve el ON p.ideleve = el.ideleve "
	 		+ " JOIN tab_Intermedaireclasse ic ON el.idintermedaireclasse = ic.idintermedaireclasse "
	 		+ " JOIN tab_Classe cl ON ic.idclasse = cl.idclasse "
	 		+ " JOIN tab_Intermedaireannee ia ON el.idintermedaireannee = ia.idintermedaireannee "
	 		+ " JOIN tab_Annee an ON ia.idannee = an.idannee "
	 		+ " JOIN tab_Ecole e ON e.idecole = ia.idecole "
	 		+ "	WHERE e.idecole = :idecole  "
	 		+ " GROUP BY cl.classe, an.annee "
	 		+ " ORDER BY an.annee DESC";
	 MapSqlParameterSource parameters = new MapSqlParameterSource()
		   		.addValue("idecole", idecole);
   return namedParameterJdbcTemplate.query(query,parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
}

public ResponseEntity<?> CollectionPaiementses(long idecole) {
	List<PaiementDto> collections = CollectionPaiementse(idecole);

   if (collections.isEmpty()) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé pour cet élève.");
   } else {
       return ResponseEntity.ok(collections);
   }
	
}

public List<PaiementDto> CollectionPaiementdashbords(long idecole,long idclasse,long idannee) {
	 String query = "SELECT UPPER(cl.classe) AS classe, UPPER(an.annee) AS annee, SUM(p.montants) AS montants "
	 		+ "	FROM tab_Paiement p "
	 		+ "	JOIN tab_Eleve el ON p.ideleve = el.ideleve "
	 		+ "	JOIN tab_Intermedaireclasse ic ON el.idintermedaireclasse = ic.idintermedaireclasse "
	 		+ "	JOIN tab_Ecole a ON ic.idecole = a.idecole "
	 		+ "	JOIN tab_Classe cl ON ic.idclasse = cl.idclasse "
	 		+ "	JOIN tab_Intermedaireannee ia ON el.idintermedaireannee = ia.idintermedaireannee "
	 		+ "	JOIN tab_Annee an ON ia.idannee = an.idannee "
	 		+ "	WHERE a.idecole = :idecole AND ic.idclasse = :idclasse  AND an.idannee = :idannee "
	 		+ "	GROUP BY cl.classe, an.annee ";
	   MapSqlParameterSource parameters = new MapSqlParameterSource()
			   		.addValue("idecole", idecole)
			   		.addValue("idclasse", idclasse)
			   		.addValue("idannee", idannee);
  return namedParameterJdbcTemplate.query(query,parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
}

public ResponseEntity<?> CollectionPaiementdashbord(long idecole,long idclasse,long idannee) {
	List<PaiementDto> collections = CollectionPaiementdashbords(idecole, idclasse,idannee);

  if (collections.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé pour cet élève.");
  } else {
      return ResponseEntity.ok(collections);
  }
	
}

private List<PaiementDto> getPaiementsByEleves(long ideleve) {
	 String query = "WITH PaiementsDistincts AS ( "
	 		+ "	SELECT n.ideleve, SUM(n.montants) AS montants "
	 		+ "	FROM tab_Paiement n "
	 		+ "	JOIN tab_Eleve b ON n.ideleve = b.ideleve "
	 		+ "	JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	 		+ "	WHERE b.ideleve = :ideleve "
	 		+ "	GROUP BY n.ideleve ), "
	 		+ "	MontantsFrais AS ( "
	 		+ "	SELECT d2.idintermedaireclasse, SUM(d2.montant) AS montant "
	 		+ "	FROM tab_Frais d2 "
	 		+ "	JOIN tab_Intermedaireclasse c ON d2.idintermedaireclasse = c.idintermedaireclasse "
	 		+ "	JOIN tab_eleve b ON d2.idintermedaireclasse = c.idintermedaireclasse "
	 		+ "	WHERE b.ideleve = :ideleve "
	 		+ "	GROUP BY d2.idintermedaireclasse) "
	 		+ "	SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve,"
	 		+ "	UPPER(b.sexe) AS sexe, UPPER(a.ecole) AS ecole, e.idclasse, UPPER(e.classe) AS classe, pd.montants, mf.montant "
	 		+ "	FROM tab_Eleve b "
	 		+ "	JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	 		+ "	JOIN tab_Classe e ON c.idclasse = e.idclasse "
	 		+ "	JOIN tab_Ecole a ON c.idecole = a.idecole "
	 		+ " LEFT JOIN PaiementsDistincts pd ON b.ideleve = pd.ideleve "
	 		+ "	LEFT JOIN MontantsFrais mf ON c.idintermedaireclasse = mf.idintermedaireclasse "
	 		+ "	WHERE b.ideleve = :ideleve "
	 		+ "	ORDER BY b.nom, b.postnom, b.prenom";
MapSqlParameterSource parameters = new MapSqlParameterSource()
   .addValue("ideleve", ideleve);
return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
}
public ResponseEntity<?> getPaiementsByEleve(Long ideleve) {
	 List<PaiementDto> collections = getPaiementsByEleves(ideleve);

	   if (collections.isEmpty()) {
	       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun élève trouvé pour ce nom.");
	   } else {
	       return ResponseEntity.ok(collections);
	   }
}

public List<PaiementDto> CollectionPaiementAcomptes(Long ideleve) {
	 String query = "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, "
	 		+ " a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, n.idpaiement, "
	 		+ " c.idintermedaireclasse, d1.idintermedaireannee, f.idannee,  UPPER(n.categorie) AS categorie, "
	 		+ " UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.datepaie, UPPER(n.frais) as frais, "
	 		+ " m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune, n.montants, "
	 		+ " (SELECT SUM(d2_sub.montant) "
	 		+ " FROM tab_Frais d2_sub "
	 		+ " WHERE d2_sub.idintermedaireclasse = c.idintermedaireclasse "
	 		+ " AND d2_sub.idintermedaireannee = d1.idintermedaireannee) AS montant_frais, "
	 		+ " (SELECT SUM(n_sub.montants) "
	 		+ " FROM tab_Paiement n_sub "
	 		+ " WHERE n_sub.ideleve = b.ideleve) AS montant_paiement"
	 		+ " FROM tab_Eleve b "
	 		+ " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
	 		+ " JOIN tab_Classe e ON c.idclasse = e.idclasse "
	 		+ " JOIN tab_Ecole a ON c.idecole = a.idecole "
	 		+ " JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
	 		+ " JOIN tab_Annee f ON d1.idannee = f.idannee "
	 		+ " JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
	 		+ " JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
	 		+ " JOIN tab_Tranche l ON l.idtranche = d2.idtranche "
	 		+ " JOIN tab_Province m ON m.idprovince = b.idprovince "
	 		+ " JOIN tab_Commune h ON h.idcommune = a.idcommune "
	 		+ " JOIN tab_Paiement n ON n.ideleve = b.ideleve "
	 		+ " WHERE b.ideleve = :ideleve "
	 		+ " GROUP BY b.ideleve, a.idecole, e.idclasse, h.idcommune, m.idprovince, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais "
	 		+ " ORDER BY n.idpaiement DESC LIMIT 1";
   MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("ideleve", ideleve);
   return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
}

public ResponseEntity<?> CollectionPaiementAcompte(Long ideleve) {
	List<PaiementDto> collections = CollectionPaiementAcomptes(ideleve);

   if (collections.isEmpty()) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé pour cet élève.");
   } else {
       return ResponseEntity.ok(collections);
   }
	
}


public List<PaiementDto> CollectionPaiementSoldes(Long ideleve) {
    String query = 
        "SELECT UPPER(b.nom) AS nom, UPPER(b.postnom) AS postnom, UPPER(b.prenom) AS prenom, b.ideleve, " +
        "    a.idecole, UPPER(a.ecole) AS ecole, UPPER(a.avenue) AS avenue, e.idclasse, UPPER(e.classe) AS classe, " +
        "    n.idpaiement, c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, UPPER(n.categorie) AS categorie, " +
        "    UPPER(f.annee) AS annee, l.idtranche, UPPER(l.tranche) AS tranche, n.datepaie, UPPER(n.frais) AS frais, " +
        "    m.idprovince, UPPER(m.province) AS province, h.idcommune, UPPER(h.commune) AS commune, " +
        "    (SELECT SUM(d2_sub.montant) " +
        "     FROM tab_Frais d2_sub " +
        "     WHERE d2_sub.idintermedaireclasse = c.idintermedaireclasse AND d2_sub.idintermedaireannee = d1.idintermedaireannee) AS montant_frais, " +
        "    (SELECT SUM(n_sub.montants) " +
        "     FROM tab_Paiement n_sub " +
        "     WHERE n_sub.ideleve = b.ideleve) AS montant_paiement, " +
        "    CASE " +
        "        WHEN " +
        "            (SELECT SUM(d2_sub.montant) " +
        "             FROM tab_Frais d2_sub " +
        "             WHERE d2_sub.idintermedaireclasse = c.idintermedaireclasse AND d2_sub.idintermedaireannee = d1.idintermedaireannee) = " +
        "            (SELECT SUM(n_sub.montants) " +
        "             FROM tab_Paiement n_sub " +
        "             WHERE n_sub.ideleve = b.ideleve) " +
        "        THEN " +
        "            (SELECT SUM(n_sub.montants) " +
        "             FROM tab_Paiement n_sub " +
        "             WHERE n_sub.ideleve = b.ideleve) " +
        "        ELSE 0 " +
        "    END AS montants " +

        "FROM tab_Eleve b " +
        "JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse " +
        "JOIN tab_Classe e ON c.idclasse = e.idclasse " +
        "JOIN tab_Ecole a ON c.idecole = a.idecole " +
        "JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee " +
        "JOIN tab_Annee f ON d1.idannee = f.idannee " +
        "JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse " +
        "JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie " +
        "JOIN tab_Tranche l ON l.idtranche = d2.idtranche " +
        "JOIN tab_Province m ON m.idprovince = b.idprovince " +
        "JOIN tab_Commune h ON h.idcommune = a.idcommune " +
        "JOIN tab_Paiement n ON n.ideleve = b.ideleve " +
        "WHERE b.ideleve = :ideleve " +
        "GROUP BY b.ideleve, a.idecole, e.idclasse, h.idcommune, m.idprovince, " +
        "    c.idintermedaireclasse, d1.idintermedaireannee, f.idannee, " +
        "    k.idcategorie, l.idtranche, n.idpaiement, d2.idfrais " +
        "ORDER BY n.idpaiement DESC LIMIT 1";

    MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("ideleve", ideleve);

    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(PaiementDto.class));
}


public ResponseEntity<?> CollectionPaiementSolde(Long ideleve) {
	List<PaiementDto> collections = CollectionPaiementSoldes(ideleve);

  if (collections.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun paiement trouvé pour cet élève.");
  } else {
      return ResponseEntity.ok(collections);
  }
	
}

public void logUserPayment(String username, String noms, String classe, String ecole, String annee, double montant, String frais) {
    logger.info("Paiement effectué | Élève: {} | Classe: {} | École: {} | Année: {} | Montant: {} | Utilisateur: {}",
    		noms, classe, ecole, annee, montant, username , frais);
}

public void enregistrerPaiement(String username, String noms, String classe, String ecole, String annee, double montant, String frais) {
    logUserPayment(username, noms, classe, ecole, annee, montant, frais);
    System.out.println("✅ Paiement enregistré dans les logs !");
}






}

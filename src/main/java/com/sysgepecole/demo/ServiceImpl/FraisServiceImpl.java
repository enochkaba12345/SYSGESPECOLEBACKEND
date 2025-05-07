package com.sysgepecole.demo.ServiceImpl;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


import com.sysgepecole.demo.Dto.FraisDto;
import com.sysgepecole.demo.Dto.FraisModelDto;
import com.sysgepecole.demo.Models.Categorie_frais;
import com.sysgepecole.demo.Models.Frais;
import com.sysgepecole.demo.Models.Intermedaire_annee;
import com.sysgepecole.demo.Models.Intermedaire_classe;
import com.sysgepecole.demo.Models.Tranche;
import com.sysgepecole.demo.Repository.CategorieRepository;
import com.sysgepecole.demo.Repository.FraisRepository;
import com.sysgepecole.demo.Repository.Intermedaire_anneeRepository;
import com.sysgepecole.demo.Repository.Intermedaire_classeRepository;
import com.sysgepecole.demo.Repository.TrancheRepository;
import com.sysgepecole.demo.Service.FraisService;

@Service
public class FraisServiceImpl implements FraisService{
	

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	 @Autowired
	    private FraisRepository fraisRepository;
	 
	 @Autowired
	    private TrancheRepository trancheRepository;
	 
	 @Autowired
	    private CategorieRepository categorieRepository;

	    @Autowired
	    private Intermedaire_classeRepository intermedaireClasseRepository;

	    @Autowired
	    private Intermedaire_anneeRepository intermedaireAnneeRepository;

	   

	    @Override
	    public Frais saveFrais(FraisDto fraisDto) {
	        // Vérifier l'existence des entités associées
	        Tranche tranche = trancheRepository.findById(fraisDto.getIdtranche())
	                .orElseThrow(() -> new RuntimeException("Tranche not found with id " + fraisDto.getIdtranche()));
	        
	        Categorie_frais categorie = categorieRepository.findById(fraisDto.getIdcategorie())
	                .orElseThrow(() -> new RuntimeException("Categorie not found with id " + fraisDto.getIdcategorie()));

	        Intermedaire_classe intermedaireClasse = intermedaireClasseRepository.findById(fraisDto.getIdintermedaireclasse())
	                .orElseThrow(() -> new RuntimeException("IntermedaireClasse not found with id " + fraisDto.getIdintermedaireclasse()));

	        Intermedaire_annee intermedaireAnnee = intermedaireAnneeRepository.findById(fraisDto.getIdintermedaireannee())
	                .orElseThrow(() -> new RuntimeException("IntermedaireAnnee not found with id " + fraisDto.getIdintermedaireannee()));

	        Frais frais = new Frais();
	        frais.setMontant(fraisDto.getMontant());
	        frais.setTranche(tranche);
	        frais.setCategorie(categorie);
	        frais.setIntermedaireClasse(intermedaireClasse);
	        frais.setIntermedaireAnnee(intermedaireAnnee);
	        frais.setIduser(fraisDto.getIduser());

	        return fraisRepository.save(frais);
	    }
	    
	    @Override
	    public Frais updateFrais(Long idfrais, FraisDto fraisDto) {
	      
	        Frais frais = fraisRepository.findById(idfrais)
	                .orElseThrow(() -> new RuntimeException("Frais not found with id " + idfrais));

	       
	        Tranche tranche = trancheRepository.findById(fraisDto.getIdtranche())
	                .orElseThrow(() -> new RuntimeException("Tranche not found with idtranche " + fraisDto.getIdtranche()));
	        
	        Categorie_frais categorie = categorieRepository.findById(fraisDto.getIdcategorie())
	                .orElseThrow(() -> new RuntimeException("Categorie not found with idcategorie " + fraisDto.getIdcategorie()));

	        Intermedaire_classe intermedaireClasse = intermedaireClasseRepository.findById(fraisDto.getIdintermedaireclasse())
	                .orElseThrow(() -> new RuntimeException("IntermedaireClasse not found with idinteremdaireclasse " + fraisDto.getIdintermedaireclasse()));

	        Intermedaire_annee intermedaireAnnee = intermedaireAnneeRepository.findById(fraisDto.getIdintermedaireannee())
	                .orElseThrow(() -> new RuntimeException("IntermedaireAnnee not found with idintermedaireanne " + fraisDto.getIdintermedaireannee()));

	       
	        frais.setMontant(fraisDto.getMontant());
	        frais.setTranche(tranche);
	        frais.setCategorie(categorie);
	        frais.setIntermedaireClasse(intermedaireClasse);
	        frais.setIntermedaireAnnee(intermedaireAnnee);
	        frais.setIduser(frais.getIduser());

	        return fraisRepository.save(frais);
	    }
	    
	    public List<FraisModelDto> CollecteTranches() {
		    String query = "SELECT e.idtranche,e.tranche"
		    		+ " FROM tab_Tranche e"
		    		+ " ORDER BY e.idtranche asc";
		    return namedParameterJdbcTemplate.query(query, new BeanPropertyRowMapper<>(FraisModelDto.class));
		}
		
		public ResponseEntity<?> CollecteTranche() {
			  List<FraisModelDto> collections = CollecteTranches();

			    if (collections.isEmpty()) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune niveau trouvée.");
			    } else {
			        return ResponseEntity.ok(collections);
			    }
		}
		
		
		   public List<FraisModelDto> CollecteEcoleTranches(long idecole) {
			   String query = " SELECT DISTINCT l.idtranche, UPPER(l.tranche) AS tranche "
			    		+ " FROM tab_Eleve b "
			    		+ " JOIN tab_Intermedaireclasse c ON b.idintermedaireclasse = c.idintermedaireclasse "
			    		+ " JOIN tab_Classe e ON c.idclasse = e.idclasse "
			    		+ " JOIN tab_Ecole a ON c.idecole = a.idecole "
			    		+ " JOIN tab_Intermedaireannee d1 ON b.idintermedaireannee = d1.idintermedaireannee "
			    		+ " JOIN tab_Annee f ON d1.idannee = f.idannee "
			    		+ " JOIN tab_Frais d2 ON b.idintermedaireannee = d2.idintermedaireannee AND d2.idintermedaireclasse = c.idintermedaireclasse "
			    		+ " JOIN tab_Categoriefrais k ON k.idcategorie = d2.idcategorie "
			    		+ " JOIN tab_Tranche l ON l.idtranche = d2.idtranche  "
			    		+ " WHERE a.idecole = :idecole "
			    		+ " ORDER BY l.idtranche";
			    MapSqlParameterSource parameters = new MapSqlParameterSource()
				        .addValue("idecole", idecole);
				    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(FraisModelDto.class));

		   }
			
			public ResponseEntity<?> CollecteEcoleTranche(long idecole) {
				  List<FraisModelDto> collections = CollecteEcoleTranches(idecole);

				    if (collections.isEmpty()) {
				        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune niveau trouvée.");
				    } else {
				        return ResponseEntity.ok(collections);
				    }
			}
		
		  public List<FraisModelDto> CollecteClasses(Long idtranche) {
			    String query = "SELECT b.idtranche, UPPER(b.tranche) AS tranche, a.idecole, UPPER(a.ecole) AS tranche, e.idclasse,"
			    		+ "	UPPER(e.classe) AS classe, c.idintermedaireclasse"
			    		+ "	FROM tab_Ecole a"
			    		+ "	JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole"
			    		+ "	JOIN tab_Classe e ON e.idclasse = c.idclasse"
			    		+" JOIN tab_Frais f ON f.idintermedaireclasse = c.idintermedaireclasse"
			    		+" JOIN tab_Tranche b ON b.idtranche = f.idtranche"
			    		+ "	WHERE b.idtranche =:idtranche"
			    		+" GROUP BY e.idclasse,b.idtranche,c.idintermedaireclasse,a.idecole"
			    		+ "	ORDER BY e.idclasse ASC";
			    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("idtranche", idtranche);
			    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(FraisModelDto.class));
			}
			
			public ResponseEntity<?> CollecteClasse(Long idtranche) {
				  List<FraisModelDto> collections = CollecteClasses(idtranche);

				    if (collections.isEmpty()) {
				        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune classe trouvée.");
				    } else {
				        return ResponseEntity.ok(collections);
				    }
			}
			
			
			 public List<FraisModelDto> CollecteEcoleTrancheClasses(long idecole,long idtranche) {
				    String query = "SELECT b.idtranche, UPPER(b.tranche) AS tranche, a.idecole, UPPER(a.ecole) AS tranche, e.idclasse,"
				    		+ "	UPPER(e.classe) AS classe, c.idintermedaireclasse"
				    		+ "	FROM tab_Ecole a"
				    		+ "	JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole"
				    		+ "	JOIN tab_Classe e ON e.idclasse = c.idclasse"
				    		+" JOIN tab_Frais f ON f.idintermedaireclasse = c.idintermedaireclasse"
				    		+" JOIN tab_Tranche b ON b.idtranche = f.idtranche"
				    		+ "	WHERE b.idtranche =:idtranche AND a.idecole =:idecole"
				    		+" GROUP BY e.idclasse,b.idtranche,c.idintermedaireclasse,a.idecole"
				    		+ "	ORDER BY e.idclasse ASC";
				    MapSqlParameterSource parameters = new MapSqlParameterSource()
				    		.addValue("idecole", idecole)
				    		.addValue("idtranche", idtranche);
				    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(FraisModelDto.class));
				}
				
				public ResponseEntity<?> CollecteEcoleTrancheClasse(long idecole,long idtranche) {
					  List<FraisModelDto> collections = CollecteEcoleTrancheClasses(idecole,idtranche);

					    if (collections.isEmpty()) {
					        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune classe trouvée.");
					    } else {
					        return ResponseEntity.ok(collections);
					    }
				}
	    
			
			public List<FraisModelDto> CollecteFraiss(Long idclasse, Long idtranche) {
			    String query = "SELECT b.idtranche, UPPER(b.tranche) AS tranche, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, "
			                 + "UPPER(e.classe) AS classe, c.idintermedaireclasse, f.idfrais, f.montant, "
			                 + "g.idcategorie, UPPER(g.categorie) AS categorie, d.idintermedaireannee, h.idannee, UPPER(h.annee) AS annee "
			                 + "FROM tab_Ecole a "
			                 + "JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole "
			                 + "JOIN tab_Classe e ON e.idclasse = c.idclasse "
			                 + "JOIN tab_Frais f ON f.idintermedaireclasse = c.idintermedaireclasse "
			                 + "JOIN tab_Intermedaireannee d ON d.idintermedaireannee = f.idintermedaireannee "
			                 + "JOIN tab_Tranche b ON b.idtranche = f.idtranche "
			                 + "JOIN tab_Annee h ON h.idannee = d.idannee "
			                 + "JOIN tab_Categoriefrais g ON g.idcategorie = f.idcategorie "
			                 + "WHERE e.idclasse = :idclasse AND b.idtranche = :idtranche "
			                 + "GROUP BY b.idtranche, a.idecole, e.idclasse, c.idintermedaireclasse, f.idfrais, f.montant, g.idcategorie, d.idintermedaireannee, h.idannee "
			                 + "ORDER BY g.idcategorie ASC";

			    MapSqlParameterSource parameters = new MapSqlParameterSource()
			            .addValue("idclasse", idclasse)
			            .addValue("idtranche", idtranche);

			    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(FraisModelDto.class));
			}

			public ResponseEntity<?> CollecteFrais(Long idclasse, Long idtranche) {
			    List<FraisModelDto> collections = CollecteFraiss(idclasse, idtranche);

			    if (collections.isEmpty()) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune classe trouvée.");
			    } else {
			        return ResponseEntity.ok(collections);
			    }
			}

			public List<FraisModelDto> CollecteFraisClasses(long idecole,long idclasse, long idtranche) {
			    String query = "SELECT b.idtranche, UPPER(b.tranche) AS tranche, a.idecole, UPPER(a.ecole) AS ecole, e.idclasse, "
			                 + "UPPER(e.classe) AS classe, c.idintermedaireclasse, f.idfrais, f.montant, "
			                 + "g.idcategorie, UPPER(g.categorie) AS categorie, d.idintermedaireannee, h.idannee, UPPER(h.annee) AS annee "
			                 + "FROM tab_Ecole a "
			                 + "JOIN tab_Intermedaireclasse c ON c.idecole = a.idecole "
			                 + "JOIN tab_Classe e ON e.idclasse = c.idclasse "
			                 + "JOIN tab_Frais f ON f.idintermedaireclasse = c.idintermedaireclasse "
			                 + "JOIN tab_Intermedaireannee d ON d.idintermedaireannee = f.idintermedaireannee "
			                 + "JOIN tab_Tranche b ON b.idtranche = f.idtranche "
			                 + "JOIN tab_Annee h ON h.idannee = d.idannee "
			                 + "JOIN tab_Categoriefrais g ON g.idcategorie = f.idcategorie "
			                 + "WHERE a.idecole = :idecole AND e.idclasse = :idclasse AND b.idtranche = :idtranche "
			                 + "GROUP BY b.idtranche, a.idecole, e.idclasse, c.idintermedaireclasse, f.idfrais, f.montant, g.idcategorie, d.idintermedaireannee, h.idannee "
			                 + "ORDER BY g.idcategorie ASC";

			    MapSqlParameterSource parameters = new MapSqlParameterSource()
			    		.addValue("idecole", idecole)
			    		.addValue("idclasse", idclasse)
			            .addValue("idtranche", idtranche);

			    return namedParameterJdbcTemplate.query(query, parameters, new BeanPropertyRowMapper<>(FraisModelDto.class));
			}

			public ResponseEntity<?> CollecteFraisClasse(long idecole,long idclasse, long idtranche) {
			    List<FraisModelDto> collections = CollecteFraisClasses(idecole,idclasse, idtranche);

			    if (collections.isEmpty()) {
			        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucune classe trouvée.");
			    } else {
			        return ResponseEntity.ok(collections);
			    }
			}

	@Override
	public ResponseEntity<Map<String, Boolean>> delete(Long idfrais) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Frais> getAllFrais() {
		// TODO Auto-generated method stub
		return null;
	}


	

}

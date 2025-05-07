package com.sysgepecole.demo.Service;



import org.springframework.http.ResponseEntity;




public interface Categorie_fraisService {

	ResponseEntity<?> getAllCategorie();
	 ResponseEntity<?> CollecteTrancheCategorie(long idtranche) ;
}

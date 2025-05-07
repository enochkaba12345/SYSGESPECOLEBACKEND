package com.sysgepecole.demo.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.sysgepecole.demo.Models.Paiement;



@Repository
public interface PaiementRepository extends  JpaRepository<Paiement, Long>{

	
    List<Paiement> findByIdeleveAndIdintermedaireclasse(long ideleve, Long idintermedaireclasse);
    

    @Query("SELECT COALESCE(SUM(p.montants), 0) FROM Paiement p WHERE p.ideleve = :ideleve AND p.idintermedaireclasse = :idintermedaireclasse")
    double getTotalMontantsByIdeleve(@Param("ideleve") Long ideleve, @Param("idintermedaireclasse") Long idintermedaireclasse);

    


}

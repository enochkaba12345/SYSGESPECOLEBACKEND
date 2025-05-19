package com.sysgepecole.demo.ServiceImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sysgepecole.demo.Dto.LogosModelDto;
import com.sysgepecole.demo.Models.Ecole;
import com.sysgepecole.demo.Models.Logos;
import com.sysgepecole.demo.Repository.EcoleRepository;
import com.sysgepecole.demo.Repository.LogosRepository;
import com.sysgepecole.demo.Service.LogosService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class LogosServiceImpl implements LogosService {

   
    private final Cloudinary cloudinary;

    @Autowired
    public LogosServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Autowired
    private EcoleRepository ecolerepository;

    @Autowired
    private LogosRepository logosrepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    

   @Override
	public Boolean createLogos(Logos logos) {
	    if (logos == null || logos.getIdecole() == null) {
	        throw new IllegalArgumentException("logos ou ID ecole ne peut pas être null.");
	    }

	    
	    Optional<Ecole> ecoleData = ecolerepository.findById(logos.getIdecole());
	    if (ecoleData.isEmpty()) {
	        System.err.println("Élève avec ID " + logos.getIdecole() + " introuvable.");
	        return false;
	    }

	    
	    Optional<Logos> existingLogos = logosrepository.findByIdecole(logos.getIdecole());
	    if (existingLogos.isPresent()) {
	        Logos logosToUpdate = existingLogos.get();
	        logosToUpdate.setLogos(logos.getLogos());
	        logosToUpdate.setIduser(logos.getIduser());
	        logosToUpdate.setIdecole(logos.getIdecole());
	        logosrepository.save(logosToUpdate);
	    } else {
	    	logosrepository.save(logos);
	    }

	    return true;
	}


    public String uploadLogos(MultipartFile logos) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(logos.getBytes(), ObjectUtils.asMap(
            "folder", "logosecole"
        ));
        return (String) uploadResult.get("secure_url");
    }

	
  @Override
public List<LogosModelDto> collecteLogos(Long idecole) {

   String baseQuery = """
            SELECT a.idecole, UPPER(a.ecole) AS ecole, y.id, h.idcommune, 
                   UPPER(h.commune) AS commune, UPPER(a.avenue) AS avenue, 
                   UPPER(p.province) AS province, 
                   COALESCE(NULLIF(y.logos, ''), 'https://res.cloudinary.com/dx7zvvxtw/image/upload/v1747295766/logo_lpf2qr.webp') AS logos
            FROM tab_Ecole a
            JOIN tab_Commune h ON h.idcommune = a.idcommune
            JOIN tab_Province p ON p.idprovince = a.idprovince
            LEFT JOIN tab_Logos y ON y.idecole = a.idecole
        """;

MapSqlParameterSource parameters = new MapSqlParameterSource();

if (idecole != null && idecole > 0) {
    baseQuery += " WHERE a.idecole = :idecole";
    parameters.addValue("idecole", idecole);
}

try {
    return namedParameterJdbcTemplate.query(
        baseQuery,
        parameters,
        new BeanPropertyRowMapper<>(LogosModelDto.class)
    );
} catch (Exception e) {
    e.printStackTrace();
    return Collections.emptyList();
}

}
}

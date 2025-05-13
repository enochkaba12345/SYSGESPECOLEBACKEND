package com.sysgepecole.demo.ServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import com.sysgepecole.demo.Dto.LogosModelDto;
import com.sysgepecole.demo.Models.Ecole;
import com.sysgepecole.demo.Models.Logos;
import com.sysgepecole.demo.Repository.EcoleRepository;
import com.sysgepecole.demo.Repository.LogosRepository;
import com.sysgepecole.demo.Service.LogosService;



@Service
public class LogosServiceImpl implements LogosService{
	
	
	@Autowired
	private EcoleRepository ecolerepository;
	
	@Autowired
	private LogosRepository logosrepository;
	
	@Autowired 
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	//private static final String UPLOAD_DIR = "https://sysgespecolebackend.onrender.com/log/";
        //private static final String UPLOAD_DIR = "C:/logos/";
	//private static final String UPLOAD_DIR = "/home/user/logos/";
  
	  @Value("${upload.dir}")
          private String uploadDir;




	@Override
        public Logos createLogos(Logos logos) {
    if (logos == null || logos.getIdecole() == null) {
        throw new IllegalArgumentException("logos ou ID ecole ne peut pas être null.");
    }

    Optional<Ecole> ecoleData = ecolerepository.findById(logos.getIdecole());
    if (ecoleData.isEmpty()) {
        System.err.println("École avec ID " + logos.getIdecole() + " introuvable.");
        return null;
    }

    Optional<Logos> existingLogos = logosrepository.findByIdecole(logos.getIdecole());
    if (existingLogos.isPresent()) {
        Logos logosToUpdate = existingLogos.get();
        logosToUpdate.setLogos(logos.getLogos());
        logosToUpdate.setIduser(logos.getIduser());
        logosToUpdate.setIdecole(logos.getIdecole());
        return logosrepository.save(logosToUpdate); 
    } else {
        return logosrepository.save(logos); 
    }
}


	
public String uploadLogos(MultipartFile logos) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);

        String originalFilename = Paths.get(logos.getOriginalFilename()).getFileName().toString();
        String filename = System.currentTimeMillis() + "-" + originalFilename;

        Path filePath = uploadPath.resolve(filename);

        logos.transferTo(filePath.toFile());

        return filename;
    }
	

	 
	
	@Override
	public List<LogosModelDto> collecteLogos(Long idecole) {
            String basePath = "https://sysgespecolebackend.onrender.com/logos/";

	    StringBuilder query = new StringBuilder();
	    query.append("SELECT ")
	         .append("a.idecole, ")
	         .append("UPPER(a.ecole) AS ecole, ")
	         .append("y.id, ")
	         .append("h.idcommune, ")
	         .append("UPPER(h.commune) AS commune, ")
	         .append("UPPER(a.avenue) AS avenue, ")
	         .append("UPPER(p.province) AS province, ")
	         .append("CONCAT('").append(basePath).append("', ")
	         .append("COALESCE(NULLIF(y.logos, ''), 'logo.jpg')) AS logos ")
	         .append("FROM tab_Ecole a ")
	         .append("JOIN tab_Commune h ON h.idcommune = a.idcommune ")
	         .append("JOIN tab_Province p ON p.idprovince = a.idprovince ")
	         .append("LEFT JOIN tab_Logos y ON y.idecole = a.idecole ");

	    MapSqlParameterSource parameters = new MapSqlParameterSource();

	    if (idecole != null && idecole > 0) {
	        query.append("WHERE a.idecole = :idecole ");
	        parameters.addValue("idecole", idecole);
	    }

	    try {
	        return namedParameterJdbcTemplate.query(
	            query.toString(),
	            parameters,
	            new BeanPropertyRowMapper<>(LogosModelDto.class)
	        );
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}


	 
	 

}

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
    public Logos createLogos(Logos logos, MultipartFile file) throws IOException {
        if (logos == null || logos.getIdecole() == null) {
            throw new IllegalArgumentException("Logos ou ID école ne peut pas être null.");
        }

        Optional<Ecole> ecoleData = ecolerepository.findById(logos.getIdecole());
        if (ecoleData.isEmpty()) {
            throw new IllegalArgumentException("École avec ID " + logos.getIdecole() + " introuvable.");
        }

        // Upload l’image sur Cloudinary et récupère le public_id
        String imagePublicId = uploadLogos(file);
        logos.setLogos(imagePublicId); 

        // Si un logo existe déjà, on le met à jour
        Optional<Logos> existingLogos = logosrepository.findByIdecole(logos.getIdecole());
        if (existingLogos.isPresent()) {
            Logos logosToUpdate = existingLogos.get();
            logosToUpdate.setLogos(imagePublicId);
            logosToUpdate.setIduser(logos.getIduser());
            logosToUpdate.setIdecole(logos.getIdecole());
            return logosrepository.save(logosToUpdate);
        } else {
            return logosrepository.save(logos);
        }
    }

    public String uploadLogos(MultipartFile logos) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(logos.getBytes(), ObjectUtils.asMap(
            "folder", "logosecole"
        ));
        return (String) uploadResult.get("secure_url");
    }

    @Override
    public List<LogosModelDto> collecteLogos(Long idecole) {
        String basePath = "https://res.cloudinary.com/dx7zvvxtw/image/upload/";

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
             .append("COALESCE(NULLIF(y.logos, ''), 'logosecole/logo.jpg')) AS logos ")
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

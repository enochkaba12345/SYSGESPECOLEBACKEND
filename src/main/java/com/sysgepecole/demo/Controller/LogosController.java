package com.sysgepecole.demo.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sysgepecole.demo.Dto.LogosModelDto;
import com.sysgepecole.demo.Models.Logos;
import com.sysgepecole.demo.Service.LogosService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/logos")
public class LogosController {
	
	
	@Autowired
	private LogosService logosService;
	
	@PostMapping("/createLogos")
	public ResponseEntity<Boolean> createLogos(@RequestBody Logos logos) {
		
	    try {
	        Boolean created = logosService.createLogos(logos);
	        return ResponseEntity.ok(created);
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(false);
	    }
	}

	@PostMapping("/uploadlogos")
public ResponseEntity<?> uploadlogos(@RequestParam("logos") MultipartFile logos) {
    try {
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(logos.getOriginalFilename());
        Files.copy(logos.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return ResponseEntity.ok(Map.of("message", "Logo uploadé avec succès", "filename", logos.getOriginalFilename()));
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erreur lors de l'enregistrement du fichier", "details", e.getMessage()));
    }
     }

 
	 @GetMapping("/collecteLogo")
	 public ResponseEntity<?> collecteLogo(@RequestParam(required = false) Long idecole) {
	        List<LogosModelDto> collections = logosService.collecteLogos(idecole);

	        if (collections.isEmpty()) {
	            return ResponseEntity.status(404).body("Aucune école trouvée.");
	        } else {
	            return ResponseEntity.ok(collections);
	        }
	    }
}

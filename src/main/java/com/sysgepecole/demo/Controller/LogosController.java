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
import java.nio.file.*;


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
  public ResponseEntity<?> createLogos(@RequestBody Logos logos) {
    if (logos.getIdecole() == null || logos.getLogos() == null || logos.getIduser() == null) {
        return ResponseEntity.badRequest().body(Map.of("error", "Données invalides"));
    }
    try {
        logosService.createLogos(logos);
        return ResponseEntity.ok(Map.of("message", "Logo enregistré avec succès"));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Échec de l'enregistrement", "details", e.getMessage()));
    }
}




//	@PostMapping("/uploadlogos")
//public ResponseEntity<?> uploadlogos(@RequestParam("logos") MultipartFile logos) {
 //   try {
     //    String uploadDir = "logs/";
    //     Path uploadPath = Paths.get(uploadDir);
      //   if (!Files.exists(uploadPath)) {
      //       Files.createDirectories(uploadPath);
      //  }
      //   Path filePath = uploadPath.resolve(logos.getOriginalFilename());
      //   Files.copy(logos.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
       //  return ResponseEntity.ok(Map.of("message", "Logo uploadé avec succès", "filename", logos.getOriginalFilename()));
   //  } catch (IOException e) {
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
     //            .body(Map.of("error", "Erreur lors de l'enregistrement du fichier", "details", e.getMessage()));
    // }
    //  }
	
          @PostMapping("/uploadlogos")
	    public ResponseEntity<?> uploadlogos(@RequestParam("logos") MultipartFile logos) {
	        try {
	            String filename = logosService.uploadLogos(logos);
	            return ResponseEntity.ok(Map.of("filename", filename));
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("Erreur lors du téléchargement de la photo.");
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

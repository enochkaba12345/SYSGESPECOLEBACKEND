package com.sysgepecole.demo.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.sysgepecole.demo.Models.Photo;
import com.sysgepecole.demo.Service.PhotoService;

import net.sf.jasperreports.engine.JRException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/photo")
public class PhotoController {

	
	@Autowired
	private PhotoService photoService;
	
	@PostMapping("/createPhoto")
	public ResponseEntity<Boolean> createPhoto(@RequestBody Photo photo) {
		
	    try {
	        Boolean created = photoService.createPhoto(photo);
	        return ResponseEntity.ok(created);
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(false);
	    }
	}


	 @PostMapping("/uploadphoto")
	    public ResponseEntity<?> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
	        try {
	            String filename = photoService.uploadPhoto(photo);

	            return ResponseEntity.ok(Map.of("filename", filename));
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("Erreur lors du téléchargement de la photo.");
	        }
	    }
	 
	 @GetMapping("/CollectePhoto")
		public ResponseEntity<?> CollectePhoto() {
			return photoService.CollectePhoto();
		}
	 
	 @GetMapping("/FichePhoto/{id}")
		public ResponseEntity<?> FichePhoto(@PathVariable Long id) throws FileNotFoundException, JRException {
			return photoService.FichePhoto(id);
		}

}

package com.sysgepecole.demo.Service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sysgepecole.demo.Models.Photo;

import net.sf.jasperreports.engine.JRException;

public interface PhotoService {
	
	 String uploadPhoto(MultipartFile photo) throws IOException;
	 Boolean createPhoto(Photo photo);
	 ResponseEntity<?> CollectePhoto();
	 ResponseEntity<?> FichePhoto(long id) throws FileNotFoundException, JRException;

}

package com.sysgepecole.demo.Service;

import java.io.IOException;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;

import com.sysgepecole.demo.Dto.LogosModelDto;
import com.sysgepecole.demo.Models.Logos;



public interface LogosService {
	
	 String uploadLogos(MultipartFile logos) throws IOException;
	 Boolean createLogos(Logos logos);
	 List<LogosModelDto> collecteLogos(Long idecole);
	 

}

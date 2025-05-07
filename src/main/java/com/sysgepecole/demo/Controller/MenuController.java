package com.sysgepecole.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysgepecole.demo.Dto.MenuDto;
import com.sysgepecole.demo.Service.MenusService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/menus")
public class MenuController {

	
	@Autowired
    private MenusService menuService;
	
	 @GetMapping("/getMenusByRole/{role}")
	    public List<MenuDto> getMenusByRole(@PathVariable String role)  {
	        return menuService.getAllMenus(role);
	    }

	
}

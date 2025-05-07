package com.sysgepecole.demo.Service;


import java.util.List;



import com.sysgepecole.demo.Dto.MenuDto;


public interface MenusService {

	List<MenuDto> getAllMenus(String role);
	

	

}
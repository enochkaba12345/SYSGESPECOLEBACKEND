package com.sysgepecole.demo.Service;


import java.util.List;

import org.springframework.http.ResponseEntity;

import com.sysgepecole.demo.Dto.UserModelDto;
import com.sysgepecole.demo.Models.Users;

public interface UserService {

	ResponseEntity<Users> updateUser(Long iduser, Users user);
	ResponseEntity<?> searchUser(String username);
	ResponseEntity<Users> updateUserIdentique(Long iduser, Users user);
	String updateStatus(Long iduser, Boolean statut);
	String updatePassword(Long iduser);
	String updatesPassword(Long iduser, String password);
	ResponseEntity<?> collectUsers(String userRole, Long idecole);
	List<UserModelDto> CollecteUsers(List<String> roles, Long idecole);
	   
	

}

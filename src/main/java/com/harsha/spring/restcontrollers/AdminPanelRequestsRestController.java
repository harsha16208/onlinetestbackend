package com.harsha.spring.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.services.AdminPanelRequestService;

@RestController
public class AdminPanelRequestsRestController {
	
	@Autowired
	private AdminPanelRequestService adminPanelRequestService;
	
	@GetMapping("/deleteuser")
	public ResponseEntity<?> deleteUser(@RequestParam String username){
		adminPanelRequestService.deleteUser(username);
		return ResponseEntity.ok(null);
	}
	
	@GetMapping("/grantorrevokeaccess")
	public ResponseEntity<?> grantOrRevokeAccess(String username){
		adminPanelRequestService.grantOrRevokeAccess(username);
		return ResponseEntity.ok(null);
	}

}

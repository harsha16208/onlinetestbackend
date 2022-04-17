package com.harsha.spring.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.services.PasswordUpdateService;
import com.harsha.spring.vo.PasswordChangeVo;

@RestController
public class PasswordUpdateRestController {

	@Autowired
	private PasswordUpdateService passwordUpdateService;
	
	@PostMapping("/changePassword")
	public ResponseEntity<?> result(@RequestBody PasswordChangeVo details){
		Map<String, String> status= new HashMap<>();
		String result= passwordUpdateService.updatePassword(details);
		status.put("status", result);
		return ResponseEntity.ok(status);
	}
}

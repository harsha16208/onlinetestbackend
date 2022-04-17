package com.harsha.spring.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.services.GetAllUserDetailsService;
import com.harsha.spring.vo.UserDetailsVo;

@RestController
public class GetAllUserDetailsRestController {

	@Autowired
	private GetAllUserDetailsService getAllUserDetailsService;
	
	@GetMapping("/getalldetails")
	public ResponseEntity<?> getAllDetails(){
		List<UserDetailsVo> allUserDetails = getAllUserDetailsService.getAllUserDetailsService();
		return ResponseEntity.ok(allUserDetails);
	}
}

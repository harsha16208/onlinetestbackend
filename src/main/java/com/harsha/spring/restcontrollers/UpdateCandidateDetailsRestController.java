package com.harsha.spring.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.services.UpdateCandidateDetailsService;
import com.harsha.spring.vo.UpdationDetailsVo;

@RestController
public class UpdateCandidateDetailsRestController {
	
	@Autowired
	private UpdateCandidateDetailsService updateCandidateDetailsService;
	
	@PostMapping("/update")
	public ResponseEntity<?> updateDetails(@RequestBody UpdationDetailsVo updationDetails) throws UsernameNotFoundException{
		updateCandidateDetailsService.updateDetails(updationDetails);
		return ResponseEntity.ok(null);
	}

}

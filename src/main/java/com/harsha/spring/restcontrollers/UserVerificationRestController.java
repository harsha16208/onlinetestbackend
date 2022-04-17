package com.harsha.spring.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.services.UserVerificationService;
import com.harsha.spring.vo.OtpVo;
import com.harsha.spring.vo.UserEmailVo;

@RestController
public class UserVerificationRestController {

	@Autowired
	private UserVerificationService userVerificationService;
	
	@PostMapping("/generateotp")
	public ResponseEntity<?> generateOtp(@Valid @RequestBody UserEmailVo userEmail) throws Exception{
		
		String result = userVerificationService.generateOtp(userEmail);
		Map<String, String> status = new HashMap<>();
		if (result.equals("success")) {
			status.put("status", result);
		}
		return ResponseEntity.ok(status);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyOtp(@RequestBody OtpVo otpDetails) throws Exception{
		
		String result = userVerificationService.verify(otpDetails);
		Map<String, String> status = new HashMap<>();
		if (result.equals("success")) {
			status.put("status", result);
		}
		return ResponseEntity.ok(status);
	}
}

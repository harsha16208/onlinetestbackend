package com.harsha.spring.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.exceptions.UserNotVerifiedException;
import com.harsha.spring.services.ForgotPasswordService;
import com.harsha.spring.services.PasswordUpdateService;
import com.harsha.spring.vo.OtpVo;
import com.harsha.spring.vo.PasswordChangeVo;

@RestController
public class ForgotPasswordRestController {

	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired
	private PasswordUpdateService passwordUpdateService;
	
	@GetMapping("/forgotpassword")
	public ResponseEntity<?> requestOtp(@RequestParam String username){
		String result = forgotPasswordService.requestOtp(username);
		Map<String, String> status = new HashMap<>();
		if (result.equals("success")) {
			status.put("status", result);
			return ResponseEntity.ok(status);
		}
		
		return ResponseEntity.ok(null);
	}
	
	@PostMapping("/verifyotp")
	public ResponseEntity<?> verifyOtp(@RequestBody OtpVo otpModel) throws UserNotVerifiedException{
		String result = forgotPasswordService.verifyOtp(otpModel);
		Map<String, String> status = new HashMap<>();
		status.put("status", result);
		return ResponseEntity.ok(status);
	}
	
	@PostMapping("/resetpassword")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordChangeVo details){
		Map<String, String> status= new HashMap<>();
		String result= passwordUpdateService.updatePassword(details);
		status.put("status", result);
		return ResponseEntity.ok(status);
	}
	
}

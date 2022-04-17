package com.harsha.spring.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OtpGenerator {

	public String generateOtp() {
		int length = 6;
		String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		String otp = "";
		
		for(int i=0; i<length; i++) {
			int index = random.nextInt(characters.length());
			otp += characters.charAt(index);
		}
		
		return otp;
		
	}
	
	public String generateNumOtp() {
		int length = 6;
		Random random = new Random();
		String otp = "";
		
		for (int i=0; i<length; i++) {
			otp += random.nextInt(10);
		}
		
		return otp;
	}
}

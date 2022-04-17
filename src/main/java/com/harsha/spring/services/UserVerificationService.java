package com.harsha.spring.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.IllegalAttemptException;
import com.harsha.spring.models.UserVerification;
import com.harsha.spring.repositories.UserVerificationRepository;
import com.harsha.spring.util.OtpGenerator;
import com.harsha.spring.vo.OtpVo;
import com.harsha.spring.vo.UserEmailVo;

@Service
public class UserVerificationService {
	
	@Autowired
	private OtpGenerator otpGenerator;
	
	@Autowired
	private UserVerificationRepository userVerificationRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	public String generateOtp(UserEmailVo userEmail) throws Exception {
		String email = userEmail.getEmail();
		String otp = otpGenerator.generateOtp();
		String subject = "Verify you account";
		String body = "Your otp is : "+otp+" will be valid for next 10 minutes";
		LocalDateTime generatedTime =  LocalDateTime.now();
		UserVerification userVerification = new UserVerification(email, otp, generatedTime);
		Optional<UserVerification> user = userVerificationRepository.findByUsername(email);
		if (user.isPresent()) {
			UserVerification userVerificationDetails = user.get();
			userVerificationRepository.delete(userVerificationDetails);
		}
		userVerificationRepository.save(userVerification);
		emailSenderService.sendEmail(email, subject, body);
		
		return "success";
	}
	
	public String verify(OtpVo otpDetails) throws Exception {
		String email = otpDetails.getEmail();
		String otp = otpDetails.getOtp();
		Optional<UserVerification> userVerificationDetails = userVerificationRepository.findByUsername(email);
		if (userVerificationDetails.isEmpty()) {
			throw new UsernameNotFoundException("No Otp request received from : "+ email );
		}
		UserVerification userVerification = userVerificationDetails.get();
		String actualOtp = userVerification.getOtp();
		LocalDateTime generatedTime = userVerification.getGeneratedTime();
		LocalDateTime dateTime = LocalDateTime.now();
		long duration = Duration.between(generatedTime, dateTime).getSeconds();
		System.out.println(generatedTime+ " "+dateTime+" "+duration );
		if (duration > 600) {
			throw new IllegalAttemptException("otp expired");
		}
		if (actualOtp.equals(otp)) {
			userVerification.setVerified(true);
			userVerificationRepository.save(userVerification);
		}
		else {
			throw new IllegalAttemptException("The otp is incorrect");
		}
		
		return "success";
	}
	
	public boolean isUserVerified(String username) {
		Optional<UserVerification> findUser = userVerificationRepository.findByUsername(username);
		if (findUser.isPresent()) {
			UserVerification user = findUser.get();
			boolean verified = user.isVerified();
			if(verified) {
				return true;
			}
		}
		return false;
	}
}

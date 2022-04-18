package com.harsha.spring.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harsha.spring.exceptions.UserNotVerifiedException;
import com.harsha.spring.models.ForgotPasswordOtpModel;
import com.harsha.spring.models.User;
import com.harsha.spring.repositories.ForgotPasswordOtpRepository;
import com.harsha.spring.repositories.UserRepository;
import com.harsha.spring.util.OtpGenerator;
import com.harsha.spring.vo.OtpVo;

@Service
public class ForgotPasswordService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private OtpGenerator otpGenerator;
	
	@Autowired
	private ForgotPasswordOtpRepository forgotPasswordOtpRepository;

	public String requestOtp(String username) {
		Optional<User> findUser = userRepository.findById(username);
		User user = null;
		ForgotPasswordOtpModel forgotPasswordOtpModel = new ForgotPasswordOtpModel();
		Optional<ForgotPasswordOtpModel> findOtp = null;
		if (!findUser.isPresent()) {
			throw new UsernameNotFoundException("No user found with given username");
		}
		user = findUser.get();
		String subject = "Forgot password Otp";
		String otp = otpGenerator.generateNumOtp();
		String body = "Your otp is "+otp+ ". This will be valid for next 10 minutes";
		forgotPasswordOtpModel.setUsername(user);
		forgotPasswordOtpModel.setOtp(otp);
		forgotPasswordOtpModel.setGeneratedAt(LocalDateTime.now());
		findOtp = forgotPasswordOtpRepository.findByUsername(user);
		if (findOtp.isPresent()) {
			forgotPasswordOtpRepository.delete(findOtp.get());
		}
		forgotPasswordOtpRepository.save(forgotPasswordOtpModel);
		
		try {
			emailSenderService.sendEmail(username, subject, body);
		} catch (Exception e) {
		}
		
		return "success";
	}

	public String verifyOtp(OtpVo otpModel) throws UserNotVerifiedException {
		Optional<User> findUser = userRepository.findById(otpModel.getEmail());
		Optional<ForgotPasswordOtpModel> findOtp;
		ForgotPasswordOtpModel forgotPasswordOtpModel;
		User user = null;
		if (!findUser.isPresent()) {
			throw new UsernameNotFoundException("No user found with given username");
		}
		user = findUser.get();
		findOtp = forgotPasswordOtpRepository.findByUsername(user);
		if (!findOtp.isPresent()) {
			throw new UserNotVerifiedException("No Request for otp");
		}
		forgotPasswordOtpModel = findOtp.get();
		
		if (forgotPasswordOtpModel.getOtp().equals(otpModel.getOtp())) {
			LocalDateTime currentTime = LocalDateTime.now();
			if (currentTime.isBefore(forgotPasswordOtpModel.getGeneratedAt().plusMinutes(10))) {
				return "success";
			}
			else {
				return "expired";
			}
		}
		return "invalid otp";
		
	}

}

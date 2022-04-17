package com.harsha.spring.services;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final Logger LOGGER = Logger.getLogger(EmailSenderService.class);
	
	public void sendEmail(String toEmail, String Subject, String body) throws Exception {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(Subject);

		try {
			javaMailSender.send(message);
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		LOGGER.info("mail sent successfully");
	}
	
	
}

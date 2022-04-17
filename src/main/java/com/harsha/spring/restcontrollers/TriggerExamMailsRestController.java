package com.harsha.spring.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.exceptions.NoExamFoundException;
import com.harsha.spring.exceptions.OrganizationDoesntExistException;
import com.harsha.spring.services.TriggerExamMailsService;
import com.harsha.spring.vo.ExamMailVo;

@RestController
public class TriggerExamMailsRestController {
	
	@Autowired
	private TriggerExamMailsService triggerExamMailsService;

	@PostMapping("/trigger/{eId}")
	public ResponseEntity<?> triggerMails(@RequestBody ExamMailVo mailContent, @PathVariable String eId ) throws NoExamFoundException, OrganizationDoesntExistException, IllegalAccessException{
		
		System.out.println("test ...");
		triggerExamMailsService.triggerMails(mailContent, eId);
		
		return ResponseEntity.ok(null);
	}
}

/*
 * This rest controller responds with exam details and exam link of current available exams
 */
package com.harsha.spring.restcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.exceptions.CandidateNotFoundException;
import com.harsha.spring.services.CurrentExamDetailsService;

@RestController
public class CurrentExamDetailsRestController {
	
	@Autowired
	private CurrentExamDetailsService currentExamDetailsService;
	
	@GetMapping("/getcurrentexamdetails")
	public ResponseEntity<?> getCurrentExamDetails(@RequestParam String cId) throws CandidateNotFoundException{
		List<Map<String, Object>> currentExamDetails = currentExamDetailsService.getCurrentExamDetails(cId);
		return ResponseEntity.ok(currentExamDetails);
	}
}

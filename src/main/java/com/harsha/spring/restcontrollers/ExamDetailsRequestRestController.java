/*
 * This rest controller sends particular exam details to the candidate on taking exam Id as a parameter
 */

package com.harsha.spring.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.services.ExamDetailsRequestService;

@RestController
public class ExamDetailsRequestRestController {
	
	@Autowired
	private ExamDetailsRequestService examDetailsRequestService;

	@GetMapping("/examdetails/{eId}")
	public ResponseEntity<?> getExamDetails(@PathVariable String eId){
		Map<String, String> examDetails = examDetailsRequestService.examDetails(eId);
		return ResponseEntity.ok(examDetails);
	}
}

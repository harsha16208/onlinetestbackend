/*
 * This Rest controller responds with the all the exam details for which a particular candidate has applied
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
import com.harsha.spring.services.ExamsAppliedDetailsService;

@RestController
public class ExamsAppliedDetailsRestController {

	@Autowired
	private ExamsAppliedDetailsService examsAppliedDetailsService;

	@GetMapping("/getexamsapplieddetails")
	public ResponseEntity<?> getExamsAppliedDetails(@RequestParam String cId) throws CandidateNotFoundException {
		List<Map<String, Object>> appliedExamDetails = examsAppliedDetailsService.getAppliedExamDetails(cId);
		return ResponseEntity.ok(appliedExamDetails);
	}

}

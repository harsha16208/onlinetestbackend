/*
 * This container returns candidates results for all the exams a candidate has appeared
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
import com.harsha.spring.services.CandidateResultsService;

@RestController
public class CandidateResultsRestController {

	@Autowired
	private CandidateResultsService candidateResultsService;
	
	@GetMapping("/getresults")
	public ResponseEntity<?> getCandidateResults(@RequestParam String cId) throws CandidateNotFoundException {
		List<Map<String, Object>> candidateResults = candidateResultsService.getCandidateResults(cId);
		return ResponseEntity.ok(candidateResults);
	}
}

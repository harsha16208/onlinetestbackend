package com.harsha.spring.restcontrollers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.services.ResultsService;

@RestController
public class ResultsRestController {

	@Autowired
	private ResultsService resultsService;

	@GetMapping("/getresults/{eId}")
	public ResponseEntity<?> getResults(@PathVariable String eId) {
		List<Map<String, Object>> results = resultsService.getResults(eId);
		return ResponseEntity.ok(results);
	}
}

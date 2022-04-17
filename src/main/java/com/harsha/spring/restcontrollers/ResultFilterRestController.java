package com.harsha.spring.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.exceptions.InsufficientDetailsException;
import com.harsha.spring.services.ResultFilterService;

@RestController
public class ResultFilterRestController {
	
	@Autowired
	private ResultFilterService resultFilterService;

	@GetMapping("/{eId}/filter")
	public ResponseEntity<?> filterCandidates(@RequestParam int cutoff, @PathVariable String eId) throws InsufficientDetailsException, IllegalAccessException{
		
		boolean result = resultFilterService.filterCandidates(cutoff, eId);
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{orgId}/{eId}/filter/trigger")
	public ResponseEntity<?> triggerMails(@PathVariable String orgId, @PathVariable String eId, @RequestParam long cutoff) throws InsufficientDetailsException, IllegalAccessException{
		
		String result = resultFilterService.triggerMails(orgId, eId, cutoff);
		Map<String, String> status = new HashMap<>();
		
		if (result.equals("success")) {
			status.put("status", result);
		}
		
		return ResponseEntity.ok(status);
	}
}

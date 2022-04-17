package com.harsha.spring.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.exceptions.OrganizationDoesntExistException;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Topic;
import com.harsha.spring.services.ExamDetailsService;

@RestController
public class ExamDetailsRestController {
	
	@Autowired
	private ExamDetailsService examDetailsService;

	@GetMapping("/getexamdetails/{orgId}")
	public ResponseEntity<?> getExamDetails(@PathVariable String orgId) throws OrganizationDoesntExistException{
		List<ExamDetails> examDetails = examDetailsService.getExamDetails(orgId);
		if (examDetails != null) {
			return ResponseEntity.ok(examDetails);
		}
		throw new OrganizationDoesntExistException("No organization found with given Id "+orgId);
	}
	
	@GetMapping("/getexamdetails/{orgId}/{eId}")
	public ResponseEntity<?> getExamDetails(@PathVariable String orgId, @PathVariable String eId) throws OrganizationDoesntExistException{
		ExamDetails examDetails = examDetailsService.getExamDetails(orgId, eId);
		if (examDetails != null) {
			return ResponseEntity.ok(examDetails);
		}
		throw new OrganizationDoesntExistException("Check Organization Id or Exam Id");
	}
	
	@GetMapping("/gettopicdetails/{eId}")
	public ResponseEntity<?> getTopicDetails(@PathVariable String eId){
		List<Topic> topicDetails = examDetailsService.getTopicDetails(eId);
		if (topicDetails.size() > 0) {
			return ResponseEntity.ok(topicDetails);
		}
		return ResponseEntity.ok(new ArrayList<>());
	}
}

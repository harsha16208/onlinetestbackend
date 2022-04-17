package com.harsha.spring.restcontrollers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.harsha.spring.models.Question;
import com.harsha.spring.services.CandidateExamRequestService;
import com.harsha.spring.vo.AnswerVo;

@RestController
public class CandidateExamRequestRestController {

	@Autowired
	private CandidateExamRequestService candidateExamRequestService;

	@GetMapping("/test/{orgId}/{eId}/{cId}")
	public ResponseEntity<?> generateQuestionPaper(@PathVariable String orgId, @PathVariable String eId,
			@PathVariable String cId) throws Exception {
		List<Map<String, List<Question>>> questionPaper = candidateExamRequestService.generateQuestionPaper(orgId, eId, cId);
		if (questionPaper == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return ResponseEntity.ok(questionPaper);
	}

	@PostMapping("/test/{orgId}/{eId}/{cId}/submit")
	public ResponseEntity<Map<String, String>> submit(@RequestBody AnswerVo answers, @PathVariable String orgId,
			@PathVariable String eId, @PathVariable String cId) throws Exception {

		String result = candidateExamRequestService.submit(answers, orgId, eId, cId);
		Map<String, String> status = new HashMap<>();
		if (result.equals("success")) {
			status.put("status", result);
		}
		return ResponseEntity.ok(status);
	}
}

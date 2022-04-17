package com.harsha.spring.restcontrollers;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.harsha.spring.services.ExamCreationService;
import com.harsha.spring.vo.ExamDetailsVo;
import com.harsha.spring.vo.QuestionVo;

@RestController
@RequestMapping("/createexam")
public class CreateExamRestController {
	
	@Autowired
	private ExamCreationService examCreationService;

	@PostMapping("/{orgId}")
	public ResponseEntity<Map<String, String>> createExam(@PathVariable String orgId, @Valid @RequestBody ExamDetailsVo examDetails) throws Exception{
		
		System.out.println(examDetails);
		
		String createExam = examCreationService.createExam(orgId, examDetails);
		Map<String, String> status= new HashMap<>();
		if (createExam == "created") {
			status.put("status" , "created");
		}
		
		return ResponseEntity.ok(status);
	}
	
	@PostMapping("/{orgId}/{eId}/postquestions")
	public ResponseEntity<Map<String, String>> postQuestions(@PathVariable String orgId, @PathVariable String eId ,@RequestBody QuestionVo questions) throws Exception{
		String postQuestionsResult = examCreationService.postQuestions(orgId,eId,questions);
		Map<String, String> status = new HashMap<>();
		if (postQuestionsResult == "success") {
			status.put("status", postQuestionsResult);
		}
		return ResponseEntity.ok(status);
	}
	
	
}

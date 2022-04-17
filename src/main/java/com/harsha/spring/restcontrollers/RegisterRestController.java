package com.harsha.spring.restcontrollers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.harsha.spring.services.RegisterService;
import com.harsha.spring.vo.CandidateVo;
import com.harsha.spring.vo.OrganizationVo;

@RestController
public class RegisterRestController {

	@Autowired
	private RegisterService registerService;

	@PostMapping("/register/organization")
	public ResponseEntity<Map<String, String>> registerAsOrganization(@Valid @RequestBody OrganizationVo organization)
			throws Exception {

		String orgId = registerService.registerAsOrganization(organization);
		Map<String, String> response = new HashMap<>();
		response.put("status", "created");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orgId).toUri();

		return ResponseEntity.created(location).body(response);
	}

	@PostMapping("/register/candidate")
	public ResponseEntity<Map<String, String>> registerAsCandidate(@Valid @RequestBody CandidateVo candidate)
			throws Exception {
		String cid = registerService.registerAsCandidate(candidate);
		Map<String, String> response = new HashMap<>();
		response.put("status", "created");
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cid).toUri();
		return ResponseEntity.created(location).body(response);
	}

	@SuppressWarnings("unused")
	@PostMapping("applyforexam/{orgId}/{eId}/{cId}")
	public ResponseEntity<Map<String, String>> candidateRegisterForExam(@PathVariable String orgId,
			@PathVariable String eId, @PathVariable String cId) throws Exception {
		Map<String, String> status = new HashMap<>();
		String result = registerService.candidateRegisterForExam(orgId, eId, cId);
		if (result == null) {
			status.put("status", "Already Registered");
		} else if (result == "expired") {
			status.put("status", "Registration Time expired");
		} else {
			status.put("status", result);
		}
		return ResponseEntity.ok(status);
	}

}

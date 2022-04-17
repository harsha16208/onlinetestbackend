package com.harsha.spring.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.spring.exceptions.OrganizationDoesntExistException;
import com.harsha.spring.services.OrganizationHomeDetailsService;

@RestController
public class OrganizationHomeDetailsRestController {

	@Autowired
	private OrganizationHomeDetailsService organizationHomeDetailService;

	@GetMapping("/gethomedetails/{orgId}")
	public ResponseEntity<?> getHomeDetails(@PathVariable String orgId) throws OrganizationDoesntExistException {
		Map<String, String> homeDetails = organizationHomeDetailService.getHomeDetails(orgId);
		return ResponseEntity.ok(homeDetails);
	}
}

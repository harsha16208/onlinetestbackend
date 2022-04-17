package com.harsha.spring.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.Role;
import com.harsha.spring.models.User;
import com.harsha.spring.repositories.CandidateRepository;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.UserRepository;
import com.harsha.spring.vo.UserDetailsVo;

@Service
public class GetAllUserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private OrganizationRepository organizationRepository;

	public List<UserDetailsVo> getAllUserDetailsService() {
		List<UserDetailsVo> userDetails = new ArrayList<>();
		List<User> allUsers = userRepository.findAll();

		allUsers.stream().forEach(user -> {
			String username = user.getUsername();
			Role role = user.getRole();
			LocalDate dateOfCreation = user.getDateOfCreation();
			String name = null;
			String mobile = null;
			Boolean isAccessGiven = null;
			if (role.getRole().equals("CANDIDATE")) {
				Candidate candidate = candidateRepository.findByUsername(user);
				name = candidate.getName();
				mobile = String.valueOf(candidate.getMobile());
			} else if (role.getRole().equals("ORG")) {
				Organization organization = organizationRepository.findByUsername(user);
				name = organization.getOrganizationName();
				mobile = String.valueOf(organization.getMobile());
				isAccessGiven = organization.isAccessGiven();
			}

			userDetails.add(new UserDetailsVo(username, role.getRole(), isAccessGiven, name, mobile, dateOfCreation));

		});

		return userDetails;
	}
}

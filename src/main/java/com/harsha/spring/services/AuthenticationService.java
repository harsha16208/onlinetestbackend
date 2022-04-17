package com.harsha.spring.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.Organization;
import com.harsha.spring.models.Role;
import com.harsha.spring.models.User;
import com.harsha.spring.repositories.CandidateRepository;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.RoleRepository;
import com.harsha.spring.repositories.UserRepository;
import com.harsha.spring.vo.CandidateVo;
import com.harsha.spring.vo.JwtResponse;
import com.harsha.spring.vo.OrganizationVo;

@Service
public class AuthenticationService {

	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public JwtResponse getJwtResponse(String token, UserDetails userDetails) {
		
		String username = userDetails.getUsername();
		String role = null;
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		Object userInfo = null;
		Optional<User> findUser = userRepository.findById(username);
		Optional<Role> findOrganizationRole = roleRepository.findById("ORG");
		Optional<Role> findCandidateRole = roleRepository.findById("CANDIDATE");
		Role candidate = null;
		Role organization = null;
		User user = null;
		Candidate candidateDetails = null;
		Organization organizationDetails = null;
		
		
		if (findCandidateRole.isPresent() && findOrganizationRole.isPresent() && findUser.isPresent()) {
			candidate = findCandidateRole.get();
			organization = findOrganizationRole.get();
			user = findUser.get();
		}
		
		SimpleGrantedAuthority orgSimpleGrantedAuthority = new SimpleGrantedAuthority(organization.getRole());
		SimpleGrantedAuthority candidateSimpleGrantedAuthority  = new SimpleGrantedAuthority(candidate.getRole());
		
		if (authorities.contains(candidateSimpleGrantedAuthority)) {
			role = candidate.getRole();
			candidateDetails = candidateRepository.findByUsername(user);
			String email = candidateDetails.getUsername().getUsername();
			String cId = candidateDetails.getId();
			String mobile = String.valueOf(candidateDetails.getMobile());
			String name = candidateDetails.getName();
			LocalDate dob = candidateDetails.getDateOfBirth();
			userInfo = new CandidateVo(email, cId, name, dob, mobile, true);
		}
		if (authorities.contains(orgSimpleGrantedAuthority)) {
			role = organization.getRole();
			organizationDetails = organizationRepository.findByUsername(user);
			String email = organizationDetails.getUsername().getUsername();
			String orgId = organizationDetails.getId();
			String mobile = String.valueOf(organizationDetails.getMobile());
			String orgName = organizationDetails.getOrganizationName();
			String orgLink = organizationDetails.getUrl();
			boolean isAccessGiven = organizationDetails.isAccessGiven();
			userInfo = new OrganizationVo(email, orgId, mobile, orgName, orgLink,isAccessGiven , true);
		}
		
		
		return new JwtResponse(token, role,userInfo);
	}
}

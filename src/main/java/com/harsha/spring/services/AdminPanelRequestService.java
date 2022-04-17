package com.harsha.spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harsha.spring.models.Organization;
import com.harsha.spring.models.User;
import com.harsha.spring.repositories.OrganizationRepository;
import com.harsha.spring.repositories.UserRepository;

@Service
public class AdminPanelRequestService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;

	public void deleteUser(String username) {
		Optional<User> findUser = userRepository.findById(username);
		if (findUser.isPresent()) {
			userRepository.deleteById(username);
		}
	}

	public void grantOrRevokeAccess(String username) {
		Optional<User> findUser = userRepository.findById(username);
		User user = null;
		if (findUser.isPresent()) {
			
			user = findUser.get();
			
			Organization organization = organizationRepository.findByUsername(user);
			organization.setAccessGiven(!organization.isAccessGiven());
			organizationRepository.save(organization);
		}
		
	}

}

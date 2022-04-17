package com.harsha.spring.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.User;
import com.harsha.spring.repositories.CandidateRepository;
import com.harsha.spring.repositories.UserRepository;
import com.harsha.spring.vo.UpdationDetailsVo;

@Service
public class UpdateCandidateDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	public void updateDetails(UpdationDetailsVo updationDetails) throws UsernameNotFoundException {
		
		Optional<User> findUser = userRepository.findById(updationDetails.getUsername());
		User user = null;
		Candidate candidate = null;
		if (findUser.isPresent()) {
			user = findUser.get();
			candidate = candidateRepository.findByUsername(user);
			candidate.setMobile(Long.parseLong(updationDetails.getMobile()));
			candidate.setName(updationDetails.getName());
			candidate.setDateOfBirth(LocalDate.parse(updationDetails.getDateOfBirth()));
			candidateRepository.save(candidate);
		} else {
			throw new UsernameNotFoundException("No user found with given username");
		}
		
		
	}
	
}

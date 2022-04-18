package com.harsha.spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.harsha.spring.models.User;
import com.harsha.spring.repositories.UserRepository;
import com.harsha.spring.vo.PasswordChangeVo;

@Service
public class PasswordUpdateService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public String updatePassword(PasswordChangeVo details) {
		Optional<User> findUser = userRepository.findById(details.getUsername());
		User user = null;
		if (!findUser.isPresent()) {
			throw new UsernameNotFoundException("No user found with given username");
		}
		user = findUser.get();
		user.setPassword(bCryptPasswordEncoder.encode(details.getPassword()));
		userRepository.save(user);
		return "updated";
	}
}

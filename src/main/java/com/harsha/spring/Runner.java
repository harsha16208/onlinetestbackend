package com.harsha.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.harsha.spring.models.Role;
import com.harsha.spring.repositories.RoleRepository;

@Component
public class Runner implements CommandLineRunner {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		roleRepository.save(new Role("ADMIN", "Can manage users"));
		roleRepository.save(new Role("ORG", "Can Create Exams for user"));
		roleRepository.save(new Role("CANDIDATE", "Can Write Exams"));

	}

}

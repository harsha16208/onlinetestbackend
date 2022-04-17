package com.harsha.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsha.spring.models.UserVerification;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Integer> {

	Optional<UserVerification> findByUsername(String username);
}

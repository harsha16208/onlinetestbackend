package com.harsha.spring.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.harsha.spring.models.ForgotPasswordOtpModel;
import com.harsha.spring.models.User;

@Repository
public interface ForgotPasswordOtpRepository extends CrudRepository<ForgotPasswordOtpModel, Integer> {
	Optional<ForgotPasswordOtpModel> findByUsername(User username);
}

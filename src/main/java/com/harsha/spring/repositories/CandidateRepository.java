package com.harsha.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harsha.spring.models.Candidate;
import com.harsha.spring.models.User;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

	Candidate findByUsername(User user);
}

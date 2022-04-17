package com.harsha.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.harsha.spring.models.ExamDetails;
import com.harsha.spring.models.Organization;

@Repository
public interface ExamDetailsRepository extends JpaRepository<ExamDetails, String> {
	
	List<ExamDetails> findByOrganization(Organization organization);
	
}

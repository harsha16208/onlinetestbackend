package com.harsha.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harsha.spring.models.Organization;
import com.harsha.spring.models.User;

public interface OrganizationRepository extends JpaRepository<Organization, String> {

	Organization findByUsername(User user);
}

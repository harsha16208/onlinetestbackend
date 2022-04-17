package com.harsha.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harsha.spring.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

}

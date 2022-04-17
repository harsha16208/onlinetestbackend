package com.harsha.spring.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.harsha.spring.generators.IdGenerator;

@Entity
@Table(name = "candidates")
public class Candidate {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_seq")
	@GenericGenerator(name = "candidate_seq", strategy = "com.harsha.spring.generators.IdGenerator", 
	parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "2" ),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "CID_"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
			
	})
	private String id;
	private String name;
	private LocalDate dateOfBirth;
	@OneToOne
	@JoinColumn(name = "username")
	private User username;
	private long mobile;
	public Candidate(){
		
	}
	
	public Candidate( String name, LocalDate dateOfBirth, User username, long mobile) {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.username = username;
		this.mobile = mobile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public User getUsername() {
		return username;
	}
	public void setUsername(User username) {
		this.username = username;
	}
	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
}

package com.harsha.spring.models;

import javax.persistence.Column;
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
@Table(name = "organizations")
public class Organization {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_seq")
	@GenericGenerator(name = "org_seq", strategy = "com.harsha.spring.generators.IdGenerator", 
	parameters = {
			@Parameter(name = IdGenerator.INCREMENT_PARAM, value = "2" ),
			@Parameter(name = IdGenerator.VALUE_PREFIX_PARAMETER, value = "ORG_"),
			@Parameter(name = IdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d")
			
	})
	@Id
	private String id;
	private String organizationName;
	private String url;
	@OneToOne
	@JoinColumn(name = "username")
	private User username;
	private Long mobile;
	@Column(columnDefinition = "boolean default false")
	private boolean isAccessGiven;
	
	public Organization()
	{
		
	}
	
	public Organization(String organizationName, String url, User username, Long mobile) {
		this.organizationName = organizationName;
		this.url = url;
		this.username = username;
		this.mobile = mobile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public User getUsername() {
		return username;
	}
	public void setUsername(User username) {
		this.username = username;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public boolean isAccessGiven() {
		return isAccessGiven;
	}

	public void setAccessGiven(boolean isAccessGiven) {
		this.isAccessGiven = isAccessGiven;
	}

	
}

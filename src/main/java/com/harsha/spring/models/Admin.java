package com.harsha.spring.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="admins")
public class Admin {
	
	@Id
	private String adminId;
	private String name;	
	@OneToOne
	@JoinColumn(name = "username")
	private User username;
	
	public Admin() {
	}
	
	public Admin(String adminId, String name, User username) {
		super();
		this.adminId = adminId;
		this.name = name;
		this.username = username;
	}
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUsername() {
		return username;
	}
	public void setUsername(User username) {
		this.username = username;
	}
}

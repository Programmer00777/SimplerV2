package com.chernikov.simpler.v2.SimplerV2.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Min(value = 0, message = "ID cannot be a negative number")
	private Integer id;
	
	@NotNull(message="A name cannot be empty")
	@Size(min = 2, max = 30, message="The length of the name must be between 2 and 30 characters")
	private String name;
	
	@NotNull(message="An email cannot be empty")
	@Email(message="You have entered an incorrect email")
	private String email;
	
	public User() {}
	
	public User(Integer id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return id + ", " + name + ", " + email;
	}
}


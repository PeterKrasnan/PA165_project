/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muni.fi.pa165project.entity;

import com.muni.fi.pa165project.enums.GenderEnum;
import com.muni.fi.pa165project.enums.UserEnum;
import com.muni.fi.pa165project.structures.LoginDetails;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 *
 * @author Radoslav Karlik
 */
@Entity
@Table(name = "trackedUser")
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	private LocalDate birthDate;
	
	private double weight;
	
	private double height;
	
	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@Enumerated(EnumType.STRING)
	private UserEnum userRole;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user", orphanRemoval = true)
	private Set<Record> activityRecords = new HashSet<>();

	@Valid
	@Embedded
	private LoginDetails loginDetails = new LoginDetails();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public UserEnum getUserRole() {
		return userRole;
	}

	public void setUserRole(UserEnum userRole) {
		this.userRole = userRole;
	}

	public Set<Record> getActivityRecords() {
		return activityRecords;
	}

	public void setActivityRecords(Set<Record> activityRecords) {
		this.activityRecords = activityRecords;
	}

	public LoginDetails getLoginDetails() {
		return loginDetails;
	}

	public void setLoginDetails(LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}
	
	public void addRecord(Record record) {
		this.activityRecords.add(record);
		record.setUser(this);
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + this.loginDetails.getUsername().hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		final User other = (User) obj;
		
		return this.loginDetails.getUsername().equals(other.loginDetails.getUsername());
	}
	
}

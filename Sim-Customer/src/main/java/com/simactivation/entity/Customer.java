package com.simactivation.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Customer {

	@Id
	private String uniqueIdNumber;

	private LocalDate dateOfBirth;

	private String emailAddress;

	private String firstName;

	private String lastName;
	
	private String state;

	private String idType;

	private Integer customeraddress;

	
	private Integer simid;

	public String getUniqueIdNumber() {
		return uniqueIdNumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUniqueIdNumber(String uniqueIdNumber) {
		this.uniqueIdNumber = uniqueIdNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public Integer getCustomeraddress() {
		return customeraddress;
	}

	public void setCustomeraddress(Integer customeraddress) {
		this.customeraddress = customeraddress;
	}

	public Integer getSimid() {
		return simid;
	}

	public void setSimid(Integer simid) {
		this.simid = simid;
	}

	public Customer() {

	}

	public Customer(String uniqueIdNumber, LocalDate dateOfBirth, String emailAddress, String firstName, String lastName,
			String idType, Integer customeraddress, Integer simid) {
		super();
		this.uniqueIdNumber = uniqueIdNumber;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idType = idType;
		this.customeraddress = customeraddress;
		this.simid = simid;
	}

}

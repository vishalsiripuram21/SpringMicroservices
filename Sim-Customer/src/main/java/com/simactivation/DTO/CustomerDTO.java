package com.simactivation.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import com.simactivation.entity.CustomerAddress;

@Validated
public class CustomerDTO {

	public CustomerDTO() {
		
	}
	
	@NotNull
	@Pattern(regexp = "[0-9]{16}",message = "Id should be 16 digit")
	private String uniqueIdNumber;
	
	@NotNull(message = "date of birth must not be null")
	@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",message = "Incorrect date of birth details")
	private String dateOfBirth;

	@NotNull
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+[.]{1}[a-zA-Z0-9.-]{2,3}$",message = "Invalid email")
	private String emailAddress;

	@NotNull
	@Length(max = 15,message = "Firstname/Lastname should be maximum of 15 characters")
	private String firstName;

	@NotNull
	@Length(max = 15,message = "Firstname/Lastname should be maximum of 15 characters")
	private String lastName;

	@NotNull(message = "idType must not be null")
	private String idType;
	
	private Integer addressId;
	
	@NotNull(message = "address must not null")
	@Length(max = 25,message = "Address should be maximum of 25 characters")
	private String address;
	
	@NotNull(message = "city must not be null")
	@Pattern(regexp = "[a-zA-Z\s]*",message = "City/State should not contain any special characters except space")
	private String city;
	
	@NotNull(message = "pincode must not be null")
	@Pattern(regexp = "[0-9]{6}",message = "Pin should be 6 digit number")
	private String pincode;
	
	@NotNull(message = "state must not be null")
	@Pattern(regexp = "[a-zA-Z\s]*",message = "City/State should not contain any special characters except space")
	private String state;

	
	@NotNull(message = "SIM id should not be null")
	private Integer simId;
	
	private SimDTO simdto;
	
	private CustomerAddress customerAddress;
	
	
	
	public CustomerAddress getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(CustomerAddress customerAddress) {
		this.customerAddress = customerAddress;
	}

	public SimDTO getSimdto() {
		return simdto;
	}

	public void setSimdto(SimDTO simdto) {
		this.simdto = simdto;
	}

	public Integer getSimId() {
		return simId;
	}

	public void setSimId(Integer simid) {
		this.simId = simid;
	}

	public String getUniqueIdNumber() {
		return uniqueIdNumber;
	}

	public void setUniqueIdNumber(String uniqueIdNumber) {
		this.uniqueIdNumber = uniqueIdNumber;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
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

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}

package com.simactivation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.simactivation.entity.Customer;
import com.simactivation.entity.CustomerIdentity;

public interface CustomerIdentityRepository extends JpaRepository<CustomerIdentity, String>{

	@Query("select cus from CustomerIdentity cus where cus.firstName=?1 and cus.lastName=?2")
	public CustomerIdentity findByName(String firstName,String lastName);
	
	@Query("select cus from CustomerIdentity cus where cus.uniqueIdNumber=?1 and cus.firstName = ?2 and cus.lastName=?3")
	public CustomerIdentity findByIdAndFirstAndLastName(String uniqueIdNumber,String firstName,String lastName);
}

package com.simactivation.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.simactivation.SimActivationConfig;
import com.simactivation.DTO.AddressUpdationDTO;
import com.simactivation.DTO.CustomerDTO;
import com.simactivation.DTO.CustomersListDTO;
import com.simactivation.DTO.IdVerificationDTO;
import com.simactivation.DTO.SimDTO;
import com.simactivation.DTO.VerificationDTo;
import com.simactivation.Exceptions.CustomerAndSimCustomException;
import com.simactivation.entity.Customer;
import com.simactivation.entity.CustomerAddress;
import com.simactivation.service.CustomerAndSimservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerAndSimservice service;

	
	@GetMapping("/cusdetails")
	public List<CustomersListDTO> getCustomer(){
		return service.getCustomers();
	}
	
	@GetMapping("/cusAddress/{address}")
	public Optional<CustomerAddress> getAddress(@PathVariable("address") Integer address){
		return service.getCustomerAddress(address);
	}
	
	@PostMapping("/customerPurchase")
	public ResponseEntity<String> purchase(@RequestBody @Valid CustomerDTO cusDto) throws CustomerAndSimCustomException {
		
		service.cusPurchase(cusDto);
		return new ResponseEntity<>("purchased successfully", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/customerVerification")
	public ResponseEntity<String> verification(@RequestBody @Valid CustomersListDTO cusDto) throws CustomerAndSimCustomException {
		service.cusVerification(cusDto);
		return new ResponseEntity<>("customer verified successfully", HttpStatus.ACCEPTED);
	}

	@PostMapping("/customerValidationUsingMail")
	public ResponseEntity<String> mailVerification(@RequestBody @Valid VerificationDTo cusDto) throws CustomerAndSimCustomException {
		service.cusMailVerification(cusDto);
		return new ResponseEntity<>("email verified successfully", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/addressUpdate")
	public ResponseEntity<String> addressUpdate(@RequestBody @Valid AddressUpdationDTO
			cusDto) throws CustomerAndSimCustomException {
		service.addressUpdate(cusDto);
		return new ResponseEntity<>("address updated successfully", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/IdValidation")
	public ResponseEntity<String> IdValidation(@RequestBody @Valid IdVerificationDTO dto) throws CustomerAndSimCustomException{
		service.Idverification(dto);
		return new ResponseEntity<>("SIM activated successfully", HttpStatus.ACCEPTED);
	}
	

}

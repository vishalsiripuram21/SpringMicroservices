package com.simactivation.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.simactivation.DTO.SimDTO;
import com.simactivation.controllers.CustomerFeign;
import com.simactivation.entity.Customer;
import com.simactivation.entity.CustomerAddress;
import com.simactivation.entity.CustomerIdentity;
import com.simactivation.repository.CustomerAddressRepository;
import com.simactivation.repository.CustomerIdentityRepository;
import com.simactivation.repository.CustomerRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.vavr.concurrent.Future;

@Service
public class CustomerCircuitBreakerService {

	
	@Autowired
	CustomerFeign custFeign;

	@Autowired
	CustomerAddressRepository addrepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	CustomerIdentityRepository customerIdRepo;

	@CircuitBreaker(name = "sim-customerService")
	public Future<SimDTO> getSimDetails(Integer simId) {
		return Future.of(() -> custFeign.getSimDetails(simId));
	}

	@CircuitBreaker(name = "sim-customerService")
	public Future<Optional<CustomerAddress>> getAddress(Integer id) {
		return Future.of(() -> addrepo.findById(id));
	}

	@CircuitBreaker(name = "sim-customerService")
	public Future<Boolean> getSimStatus(Integer simId) {
		return Future.of(() -> custFeign.getSimStatus(simId));
	}

	@CircuitBreaker(name = "sim-customerService")
	public Future<Optional<Customer>> getCustomer(String uniqueID) {
		return Future.of(() -> customerRepo.findById(uniqueID));
	}

	@CircuitBreaker(name = "sim-customerService")
	public Future<Customer> findBySimid(Integer simId) {
		return Future.of(() -> customerRepo.findBySimid(simId));
	}

	@CircuitBreaker(name = "sim-customerService")
	public Future<Optional<CustomerIdentity>> identityById(String id) {
		return Future.of(() -> customerIdRepo.findById(id));
	}
}

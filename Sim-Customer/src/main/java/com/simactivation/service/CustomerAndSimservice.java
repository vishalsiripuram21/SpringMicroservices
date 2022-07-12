package com.simactivation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.simactivation.DTO.AddressUpdationDTO;
import com.simactivation.DTO.CustomerDTO;
import com.simactivation.DTO.CustomersListDTO;
import com.simactivation.DTO.IdVerificationDTO;
import com.simactivation.DTO.SimDTO;
import com.simactivation.DTO.VerificationDTo;
import com.simactivation.Exceptions.CustomerAndSimCustomException;
import com.simactivation.entity.Customer;
import com.simactivation.entity.CustomerAddress;
import com.simactivation.entity.CustomerIdentity;

import com.simactivation.repository.CustomerAddressRepository;
import com.simactivation.repository.CustomerIdentityRepository;
import com.simactivation.repository.CustomerRepository;

import io.vavr.concurrent.Future;

@Service
@Transactional
public class CustomerAndSimservice {

	@Autowired
	CustomerAddressRepository cusAddrepo;

	@Autowired
	CustomerIdentityRepository cusidentityRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	CustomerCircuitBreakerService circuitBreaker;

	public Optional<CustomerAddress> getCustomerAddress(Integer id) {
		return cusAddrepo.findById(id);
	}

	public void cusVerification(CustomersListDTO cusDto) throws CustomerAndSimCustomException {
		Customer customer = customerRepo.findByDobAndMail(LocalDate.parse(cusDto.getDateOfBirth()),
				cusDto.getEmailAddress());
		if (customer == null) {
			throw new CustomerAndSimCustomException("No request placed for you.Please register first");
		} else {
			CustomerIdentity customerIdentity = new CustomerIdentity();
			customerIdentity.setFirstName(customer.getFirstName());
			customerIdentity.setLastName(customer.getLastName());
			customerIdentity.setDateOfbirth(customer.getDateOfBirth());
			customerIdentity.setEmailAddress(customer.getEmailAddress());
			customerIdentity.setState(customer.getState());
			customerIdentity.setUniqueIdNumber(customer.getUniqueIdNumber());
			cusidentityRepo.save(customerIdentity);
		}
	}

	public void cusMailVerification(VerificationDTo cusDto) throws CustomerAndSimCustomException {
		CustomerIdentity customer = cusidentityRepo.findByName(cusDto.getFirstName(), cusDto.getLastName());
		if (customer == null) {
			throw new CustomerAndSimCustomException("No customer found for the provided details");
		} else if (!customer.getEmailAddress().equals(cusDto.getEmailAddress())) {
			throw new CustomerAndSimCustomException("Invalid email details!!");
		}

	}

	public void addressUpdate(AddressUpdationDTO cusDto) throws CustomerAndSimCustomException {
		Future<Optional<Customer>> customer = circuitBreaker.getCustomer(cusDto.getUniqueIdNumber());
		Future<Optional<CustomerIdentity>> cusIdentity = circuitBreaker.identityById(cusDto.getUniqueIdNumber());
		CustomerAddress customerAddress = new CustomerAddress();
		customerAddress.setAddress(cusDto.getAddress());
		customerAddress.setCity(cusDto.getCity());
		customerAddress.setPincode(cusDto.getPincode());
		customerAddress.setState(cusDto.getState());
		if (customer == null)
			throw new CustomerAndSimCustomException("no customer found with Id");
		else {
			System.err.println("1");
			Integer original = customer.get().get().getCustomeraddress();
			CustomerAddress address =  cusAddrepo.save(customerAddress);
			customer.get().get().setState(cusDto.getState());
			customer.get().get().setCustomeraddress(address.getAddressId());
			cusIdentity.get().get().setState(cusDto.getState());
			customerRepo.save(customer.get().get());
			cusidentityRepo.save(cusIdentity.get().get());
			
		}

	}

	public void cusPurchase(CustomerDTO cusDto) throws CustomerAndSimCustomException {
		Future<Boolean> details = circuitBreaker.getSimStatus(cusDto.getSimId());

		Future<Optional<Customer>> duplicate = circuitBreaker.getCustomer(cusDto.getUniqueIdNumber());

		Future<Customer> duplicateSim = circuitBreaker.findBySimid(cusDto.getSimId());

		if (!duplicate.get().isPresent() && details.get() && duplicateSim.get() == null) {
			System.err.println("present : ");
			Customer cusObj = new Customer();
			cusObj.setSimid(cusDto.getSimId());
			cusObj.setFirstName(cusDto.getFirstName());
			cusObj.setLastName(cusDto.getLastName());
			cusObj.setDateOfBirth(LocalDate.parse(cusDto.getDateOfBirth()));
			cusObj.setEmailAddress(cusDto.getEmailAddress());
			cusObj.setUniqueIdNumber(cusDto.getUniqueIdNumber());
			cusObj.setIdType(cusDto.getIdType());
			CustomerAddress address = new CustomerAddress();
			address.setAddress(cusDto.getAddress());
			address.setCity(cusDto.getCity());
			address.setPincode(cusDto.getPincode());
			address.setState(cusDto.getState());
			CustomerAddress insertedAddressObj = cusAddrepo.save(address);
			cusObj.setCustomeraddress(insertedAddressObj.getAddressId());
			cusObj.setState(insertedAddressObj.getState());
			customerRepo.save(cusObj);
		} else if (duplicate.get().isPresent()) {
			throw new CustomerAndSimCustomException("Customer with Id already existed");
		} else if (!details.get()) {
			
			throw new CustomerAndSimCustomException("Sim card already active");
		} else if (duplicateSim.get() != null) {
			throw new CustomerAndSimCustomException("SIM already purchased");
		}
	}

	public void Idverification(IdVerificationDTO dto) throws CustomerAndSimCustomException {
		System.err.println("inside");
		Customer customer = customerRepo.findByIdAndFirstAndLastName(dto.getUniqueIdNumber(), dto.getFirstName(),
				dto.getLastName());
		if (customer == null)
			throw new CustomerAndSimCustomException("please register for a sim!!details not found");
		else {
			CustomerIdentity id = cusidentityRepo.findByIdAndFirstAndLastName(dto.getUniqueIdNumber(),
					dto.getFirstName(), dto.getLastName());

			if (id != null) {
				Boolean simObject = restTemplate.getForObject("http://Sim/sim/getSimStatus/" + customer.getSimid(),
						Boolean.class);
				if (!simObject) {
					throw new CustomerAndSimCustomException("SIM already in active state");
				} else {
					Boolean setStatus = restTemplate.getForObject(
							"http://Sim/sim/setStatus/" + customer.getSimid() + "/" + "active", Boolean.class);
				}
			} else {
				throw new CustomerAndSimCustomException("please verify first");
			}
		}
	}

	public List<CustomersListDTO> getCustomers() {
		List<Customer> customerList = customerRepo.findAll();
		List<CustomersListDTO> customers = new ArrayList<>();
		for (Customer customer : customerList) {
			Integer simid = customer.getSimid();
			long simStart = System.currentTimeMillis();
			Future<SimDTO> simObj = circuitBreaker.getSimDetails(simid);
			Future<Optional<CustomerAddress>> address = circuitBreaker.getAddress(customer.getCustomeraddress());
			CustomersListDTO dto = new CustomersListDTO();
			dto.setCustomerAddress(address.get().get());
			dto.setSimDetails(simObj.get());
			dto.setUniqueIdNumber(customer.getUniqueIdNumber());
			dto.setDateOfBirth(customer.getDateOfBirth().toString());
			dto.setEmailAddress(customer.getEmailAddress());
			dto.setFirstName(customer.getFirstName());
			dto.setLastName(customer.getLastName());
			dto.setIdType(customer.getIdType());
			customers.add(dto);

		}
		return customers;
	}

}

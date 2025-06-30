package com.deiziane.eventostec.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deiziane.eventostec.api.domain.address.Address;
import com.deiziane.eventostec.api.domain.event.Event;
import com.deiziane.eventostec.api.domain.event.EventRequestDTO;
import com.deiziane.eventostec.api.domain.repositories.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	public Address createAddress(EventRequestDTO data, Event event) {
		Address address = new Address();
		address.setCity(data.city());
		address.setUf(data.uf());
		address.setEvent(event);
		
		
		return addressRepository.save(address);
	}
}

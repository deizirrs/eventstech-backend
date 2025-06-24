package com.deiziane.eventostec.api.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deiziane.eventostec.api.domain.address.Address;

public interface AddressRepository extends JpaRepository<Address, UUID> {

}

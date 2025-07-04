package com.deiziane.eventostec.api.domain.address;

import java.util.UUID;

import com.deiziane.eventostec.api.domain.event.Event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "address")

public class Address {
	
	@Id
	@GeneratedValue	
	private UUID id;
	private String city;
	private String uf;
	
		
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
}
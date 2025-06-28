package com.deiziane.eventostec.api.domain.service;


import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deiziane.eventostec.api.domain.coupon.Coupon;
import com.deiziane.eventostec.api.domain.coupon.CouponRequestDTO;
import com.deiziane.eventostec.api.domain.event.Event;
import com.deiziane.eventostec.api.domain.repositories.CouponRepository;
import com.deiziane.eventostec.api.domain.repositories.EventRepository;

@Service
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private EventRepository eventRepository;

	public Coupon createCoupon( UUID eventId, CouponRequestDTO couponData) {

		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

		Coupon coupon = new Coupon();
		coupon.setCode(couponData.code());
		coupon.setDiscount(couponData.discount());
		coupon.setValid(new Date(couponData.valid()));
		coupon.setEvent(event);
		
		return couponRepository.save(coupon);

	}

}

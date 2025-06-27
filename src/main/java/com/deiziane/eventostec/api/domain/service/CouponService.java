package com.deiziane.eventostec.api.domain.service;


import com.deiziane.eventostec.api.domain.event.Event;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deiziane.eventostec.api.domain.coupon.Coupon;
import com.deiziane.eventostec.api.domain.coupon.CouponRequestDTO;
import com.deiziane.eventostec.api.domain.repositories.CouponRepository;
import com.deiziane.eventostec.api.domain.repositories.EventRepository;

@Service
public class CouponService {

	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private EventRepository eventRepository;

	public Coupon createCoupon(CouponRequestDTO data) {

		Event event = eventRepository.findById(data.event_id())
				.orElseThrow(() -> new RuntimeException("Evento não encontrado"));

		Coupon newCoupon = new Coupon();
		newCoupon.setCode(data.code());
		newCoupon.setDiscount(data.discount());
		newCoupon.setValid(new Date(data.valid()));
		newCoupon.setEvent(event);
		
		return couponRepository.save(newCoupon);

	}

}

package com.deiziane.eventostec.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deiziane.eventostec.api.domain.coupon.Coupon;
import com.deiziane.eventostec.api.domain.coupon.CouponRequestDTO;
import com.deiziane.eventostec.api.domain.service.CouponService;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {
	
	@Autowired
	private CouponService couponService;
	
	@PostMapping
	public ResponseEntity<Coupon> create(@RequestBody CouponRequestDTO body){
		Coupon  newCoupon = this.couponService.createCoupon(body);
		
		return ResponseEntity.ok(newCoupon);
	}

}

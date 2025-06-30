package com.deiziane.eventostec.api.domain.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deiziane.eventostec.api.domain.coupon.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID>{
	List<Coupon> findByEventIdAndValidAfter(UUID eventId, Date currentDate);

}

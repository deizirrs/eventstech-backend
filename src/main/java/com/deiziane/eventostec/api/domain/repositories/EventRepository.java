package com.deiziane.eventostec.api.domain.repositories;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.deiziane.eventostec.api.domain.event.Event;

public interface EventRepository extends JpaRepository<Event, UUID>{

	@Query("SELECT e FROM Event e WHERE e.date >= :currentDate")
	public Page<Event> findUpComingEvents(@Param("currentDate") Date currentDate, Pageable pageable);

}

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

	@Query("SELECT e FROM Event e LEFT JOIN FETCH e.address a WHERE e.date >= :currentDate")
	Page<Event> findUpComingEvents(@Param("currentDate") Date currentDate, Pageable pageable);

	

	@Query("""
		    SELECT e FROM Event e
		    LEFT JOIN e.address a
		    WHERE e.date >= :startDate 
		      AND (:title = '' OR e.title LIKE %:title%) 
		      AND (:city = '' OR a.city LIKE %:city%) 
		      AND (:uf = '' OR a.uf LIKE %:uf%)
		      AND e.date <= :endDate
		""")

    Page<Event> findFilteredEvents(@Param("title") String title,
		   						  @Param("city") String city,
		   						  @Param("uf") String uf,
		   						  @Param("startDate") Date startDate,
		   						  @Param("endDate") Date endDate,
		   						  Pageable pageable);
		   						  		

}

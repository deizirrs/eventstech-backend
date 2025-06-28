package com.deiziane.eventostec.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deiziane.eventostec.api.domain.event.Event;
import com.deiziane.eventostec.api.domain.event.EventRequestDTO;
import com.deiziane.eventostec.api.domain.event.EventResponseDTO;
import com.deiziane.eventostec.api.domain.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<Event> create(@RequestParam("title") String title,
										@RequestParam(value = "description",  required = false) String description,
										@RequestParam("date") Long date,
										@RequestParam("city") String city,
										@RequestParam("uf") String uf,
										@RequestParam("remote") Boolean remote,
										@RequestParam("eventUrl") String eventUrl,
										@RequestParam(value = "image", required = false) MultipartFile image){
		EventRequestDTO eventRequestDTO = new EventRequestDTO(title, description, date, city, uf, remote, eventUrl, image);
		Event newEvent = this.eventService.createEvent(eventRequestDTO);
		return ResponseEntity.ok(newEvent);
	
}
	@GetMapping()
	public ResponseEntity<List<EventResponseDTO>> getEvent(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
	List<EventResponseDTO> allEvents = this.eventService.getEvents(page, size);
	
	return ResponseEntity.ok(allEvents);
		
	}
}

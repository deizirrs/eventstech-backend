package com.deiziane.eventostec.api.domain.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.deiziane.eventostec.api.domain.coupon.Coupon;
import com.deiziane.eventostec.api.domain.event.Event;
import com.deiziane.eventostec.api.domain.event.EventDetailsDTO;
import com.deiziane.eventostec.api.domain.event.EventDetailsDTO.CouponDTO;
import com.deiziane.eventostec.api.domain.event.EventRequestDTO;
import com.deiziane.eventostec.api.domain.event.EventResponseDTO;
import com.deiziane.eventostec.api.domain.repositories.EventRepository;

@Service
public class EventService {
	@Value("${aws.bucket.name}")
	private String bucketName;

	@Autowired
	private AmazonS3 s3client;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private CouponService couponService;


	public Event createEvent(EventRequestDTO data) {
		String imgUrl = "";

		if (data.image() != null) {
			imgUrl = this.uploadImg(data.image());

		}

		Event newEvent = new Event();
		newEvent.setTitle(data.title());
		newEvent.setDescription(data.description());
		newEvent.setEventUrl(data.eventUrl());
		newEvent.setRemote(data.remote());
		newEvent.setDate(new Date(data.date()));
		newEvent.setImgUrl(imgUrl);

	  Event savedEvent = eventRepository.save(newEvent);
	  
	 if(!data.remote()) {
		 this.addressService.createAddress(data, newEvent);
	 }
	  
	  return savedEvent;
	}

	public List<EventResponseDTO> getUpComingEvents(int page, int size){
		
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Event> eventsPage = this.eventRepository.findUpComingEvents(new Date(), pageable);
		return eventsPage.map(event -> new EventResponseDTO(
											event.getId(),
											event.getTitle(),
											event.getDescription(),
											event.getDate(),
											event.getAddress() != null ? event.getAddress().getCity(): "",
											event.getAddress() != null ? event.getAddress().getUf(): "",
											event.getRemote(),
											event.getEventUrl(),
											event.getImgUrl()))
											.stream().toList();
	}
	
	   public EventDetailsDTO getEventsDetails(UUID eventId) {
	       Event event = eventRepository.findById(eventId)
	             .orElseThrow(() -> new IllegalArgumentException("Event not found"));
	       
		List<Coupon> coupons = couponService.consultCoupons(eventId, new Date());
		
		List<CouponDTO> couponDTOs = coupons.stream()
				.map(coupon -> new CouponDTO(
						coupon.getCode(), 
						coupon.getDiscount(),
						coupon.getValid())).collect(Collectors.toList());
		
		return new EventDetailsDTO(
				event.getId(),
				event.getTitle(),
				event.getDescription(),
				event.getDate(),
				event.getAddress() != null ? event.getAddress().getCity() : "",
				event.getAddress() != null ? event.getAddress().getUf() : "",
				event.getImgUrl(),
				event.getEventUrl(),
				couponDTOs);			
	}

	public List<EventResponseDTO> getFilteredEvents(int page, int size, String title, String city, String uf, Date startDate, Date endDate){
		
	    city = (city != null) ? city : "";
	    uf = (uf != null) ? uf : "";
	    title = (title != null) ? title : "";
	    startDate = (startDate != null) ? startDate : new Date(0);
	    endDate = (endDate != null) ? endDate : new Date();

				
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Event> eventsPage = this.eventRepository.findFilteredEvents(title, city, uf, startDate, endDate, pageable);

		return eventsPage.map(event -> new EventResponseDTO(
											event.getId(),
											event.getTitle(),
											event.getDescription(),
											event.getDate(),
											event.getAddress() != null ? event.getAddress().getCity(): "",
											event.getAddress() != null ? event.getAddress().getUf(): "",
											event.getRemote(),
											event.getEventUrl(),
											event.getImgUrl()))
											.stream().toList();
	}

	
	private String uploadImg(MultipartFile multipartFile) {

		String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

		try {
			File file = this.convertMultipartToFile(multipartFile);
			s3client.putObject(bucketName, fileName, file);
			file.delete();

			return s3client.getUrl(bucketName, fileName).toString();
		} catch (Exception e) {
			System.out.println("Erro ao subir o arquivo.");
			return "";
		}

	}

	private File convertMultipartToFile(MultipartFile multipartFile) throws IOException {
		File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(multipartFile.getBytes());
		fos.close();

		return convFile;

	}
}

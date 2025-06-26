package com.deiziane.eventostec.api.domain.event;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public record EventRequestDTO(String title, String description, Long date, String city, String uf, boolean remote, String eventUrl, MultipartFile image) {

}

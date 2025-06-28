package com.deiziane.eventostec.api.domain.event;

import java.util.Date;
import java.util.UUID;

public record EventResponseDTO(UUID id, String title, String description, Date date, String city, String uf, boolean remote, String eventUrl, String imgUrl) {

}

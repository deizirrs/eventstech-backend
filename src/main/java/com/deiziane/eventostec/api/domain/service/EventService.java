package com.deiziane.eventostec.api.domain.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.deiziane.eventostec.api.domain.event.Event;
import com.deiziane.eventostec.api.domain.event.EventRequestDTO;

@Service
public class EventService {
	@Value("${aws.bucket.name}")
	private String bucketName;

	@Autowired
	private AmazonS3 s3client;

	public Event createEvent(EventRequestDTO data) {
		String imgUrl = null;

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
		return newEvent;
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
			return null;
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

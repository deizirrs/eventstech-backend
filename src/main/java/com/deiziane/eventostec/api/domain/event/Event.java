package com.deiziane.eventostec.api.domain.event;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table (name = "event" ) //mapeia o nome da tabela
@Entity //preciso dizer que ela e uma entidade, representando as entrada da minha tabela como obejetos

//colocando anotaçoes via lombok para gera AUTO nosso gettes e setters
@Setter
@Getter

//premite criar instancias
@NoArgsConstructor
@AllArgsConstructor

public class Event {
	
	@Id
	@GeneratedValue //para dizer que é um valor gerado pela minha propria tabela (Gerado AUTO a cada nova entrada na tabela)
	private UUID id;
	private String title;
	private String description;
	private String imgURL;
	private String eventURL;
	private Boolean remote;
	private Date date;
	

}

package com.nttdata.nova.bookStore.dto;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.nova.bookStore.entity.Editorial;

public class EditorialDTOJsonResponse extends RepresentationModel<EditorialDTOJsonResponse> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
	
	public EditorialDTOJsonResponse() {
		
	}
	
	public EditorialDTOJsonResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public EditorialDTOJsonResponse(Editorial editorial) {
		this.id = editorial.getId();
		this.name = editorial.getName();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

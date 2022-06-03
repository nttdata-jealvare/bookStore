package com.nttdata.nova.bookStore.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.nova.bookStore.entity.Editorial;

public class EditorialDTOJsonRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("name")
	private String name;
	
	public EditorialDTOJsonRequest() {
		
	}
	
	public EditorialDTOJsonRequest(Editorial editorial) {
		this.name = editorial.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

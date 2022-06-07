package com.nttdata.nova.bookStore.dto;

import java.io.Serializable;

import java.util.Date;

import com.nttdata.nova.bookStore.collection.BookRegistry;

public class BookRegistryDTOJson implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String message;
	
	private Date date;
	
	public BookRegistryDTOJson() {
		
	}
	
	public BookRegistryDTOJson(String message, Date date) {
		this.message = message;
		this.date = date;
	}
	
	public BookRegistryDTOJson(Long id, String message, Date date) {
		this.id = id;
		this.message = message;
		this.date = date;
	}
	
	public BookRegistryDTOJson(BookRegistry book) {
		this.id = book.getId();
		this.message = book.getMessage();
		this.date = book.getDate();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}

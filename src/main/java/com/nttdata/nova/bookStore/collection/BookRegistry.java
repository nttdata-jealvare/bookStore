package com.nttdata.nova.bookStore.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import com.nttdata.nova.bookStore.dto.BookRegistryDTOJson;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

@Document(collection = "Registry")
public class BookRegistry implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String message;
	
	private Date date;
	
	public BookRegistry() {
		
	}
	
	public BookRegistry(String message, Date date) {
		this.message = message;
		this.date = date;
	}
	
	public BookRegistry(Long id, String message, Date date) {
		this.id = id;
		this.message = message;
		this.date = date;
	}
	
	public BookRegistry(BookRegistryDTOJson book) {
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

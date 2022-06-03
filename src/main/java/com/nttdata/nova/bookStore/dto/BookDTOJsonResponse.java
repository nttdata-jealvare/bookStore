package com.nttdata.nova.bookStore.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.nova.bookStore.entity.Book;

public class BookDTOJsonResponse extends RepresentationModel<BookDTOJsonResponse> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("author")
	private String author;
	
	@JsonProperty("publish")
	private Date publish;
	
	@JsonProperty("pages")
	private Integer pages;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("editorial")
	private EditorialDTOJsonResponse editorial;
	
	/**
	 * 
	 */
	public BookDTOJsonResponse() {

	}
	
	public BookDTOJsonResponse(Book book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.publish = book.getPublish();
		this.pages = book.getPages();
		this.description = book.getDescription();
		this.editorial = new EditorialDTOJsonResponse(book.getEditorial());
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublish() {
		return publish;
	}

	public void setPublish(Date publish) {
		this.publish = publish;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public EditorialDTOJsonResponse getEditorial() {
		return editorial;
	}

	public void setEditorial(EditorialDTOJsonResponse editorial) {
		this.editorial = editorial;
	}

}

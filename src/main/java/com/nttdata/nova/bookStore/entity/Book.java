package com.nttdata.nova.bookStore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nttdata.nova.bookStore.dto.BookDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.BookDTOJsonResponse;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

/**
 * 
 * */
@Entity
@Table(name="BOOK")
public class Book implements Serializable{ // serializable
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "AUTHOR")
	private String author;
	
	@Column(name = "PUBLISH")
	private Date publish;
	
	@Column(name = "PAGES")
	private Integer pages;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "editorial_id")
	private Editorial editorial;
	
	/**
	 * 
	 */
	public Book() {
		
	}
	
	public Book(BookDTOJsonRequest b) {
		this.title = b.getTitle();
		this.author = b.getAuthor();
		this.publish = b.getPublish();
		this.pages = b.getPages();
		this.description = b.getDescription();
		this.editorial = new Editorial(b.getEditorial().getId(), b.getEditorial().getName());
	}
	
	public Book(BookDTOJsonResponse b) {
		this.id = b.getId();
		this.title = b.getTitle();
		this.author = b.getAuthor();
		this.publish = b.getPublish();
		this.pages = b.getPages();
		this.description = b.getDescription();
		this.editorial = new Editorial(b.getEditorial().getId(), b.getEditorial().getName());
	}
	
	public Book(String title, String author, Date publish, Integer pages, String description,
			Editorial editorial) {
		this.title = title;
		this.author = author;
		this.publish = publish;
		this.pages = pages;
		this.description = description;
		this.editorial = editorial;
	}
	
	public Book(Long id, String title, String author, Date publish, Integer pages, String description,
			Editorial editorial) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publish = publish;
		this.pages = pages;
		this.description = description;
		this.editorial = editorial;
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

	public Editorial getEditorial() {
		return editorial;
	}

	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}
	
}

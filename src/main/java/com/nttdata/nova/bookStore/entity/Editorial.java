package com.nttdata.nova.bookStore.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * */

@Entity
@Table(name = "EDITORIAL")
public class Editorial implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	/*Relacion bases de datos*/
	@OneToMany(mappedBy = "editorial")
	private Set<Book> books;
	
	public Editorial() {
		
	}
	
	public Editorial(String name) {
		this.name = name;
	}
	
	public Editorial(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Editorial(String name, Set<Book> books) {
		this.name = name;
		this.books = books;
	}
	
	public Editorial(Long id, String name, Set<Book> books) {
		this.id = id;
		this.name = name;
		this.books = books;
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

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
}

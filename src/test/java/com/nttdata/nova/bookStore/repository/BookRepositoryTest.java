package com.nttdata.nova.bookStore.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nttdata.nova.bookStore.entity.Book;
import com.nttdata.nova.bookStore.entity.Editorial;

/**
 * 
 * @author jalvarco
 *
 */

@DataJpaTest
public class BookRepositoryTest {

	/**
	 * 
	 */
	@Autowired
	private IBookRepository bookRepository;

	/**
	 * 
	 */
	private Book book;
	private Editorial editorial;

	/**
	 * 
	 */
	private final Long id = 21L;
	private final String title = "Libro de prueba";
	private final String author = "John Smith";
	private final Date publish = new Date();
	private final Integer pages = 49;
	private final String description = "Un libro usado a modo de prueba";

	/**
	 * 
	 */
	@BeforeEach
	public void initialize() {
		this.book = new Book(title, author, publish, pages, description, editorial);
		this.editorial = new Editorial("Primera Editorial");
		this.editorial.setId(id);
	}

	/**
	 * 
	 */
	@Test
	public void addABook() {
		Book bookInserted = this.bookRepository.save(this.book);
		assertThat(bookInserted.getDescription()).isEqualTo(this.description);
		assertThat(bookInserted.getPages()).isEqualTo(this.pages);
	}
	
	/**
	 * 
	 */
	@Test
	public void getAllBooks() {
		List<Book> books = (List<Book>) this.bookRepository.findAll();
		assertThat(books).isNotNull(); // It should not be empty
		for(Book b : books) System.out.println(b.getId());
	}
	
	/**
	 * 
	 */
	@Test
	public void getBookById() {
		List<Book> books = (List<Book>) this.bookRepository.findAll();
		
		Optional<Book> bookId = this.bookRepository.findById(books.get(0).getId());
		assertThat(bookId.isPresent()).isTrue();
		assertThat(bookId.get().getId()).isEqualTo(books.get(0).getId());
	}

	/**
	 * 
	 */
	@Test
	public void getABookFromTitle() {
		String auxTitle = "Segundo libro"; // A title that we know exists

		Optional<Book> book = this.bookRepository.findByTitleIs(auxTitle);
		assertThat(book.isPresent()).isTrue(); // It should not be empty
		assertThat(book.get().getTitle()).isEqualTo(auxTitle);
	}

	/**
	 * 
	 */
	@Test
	public void getABookFromEditorial() {
		List<Book> books = (List<Book>) this.bookRepository.findByEditorialIs(this.editorial);
		assertThat(books).isNotNull(); // It should not be empty
		for (Book b : books)
			assertThat(b.getEditorial().getId()).isEqualTo(this.editorial.getId());
	}
	
	/**
	 * 
	 */
	@Test
	public void deleteAllBooks() {
		this.bookRepository.deleteAll();
		assertThat(this.bookRepository.count()).isZero();
	}
	
}

package com.nttdata.nova.bookStore.cache;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.annotation.DirtiesContext;

import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequestExtended;
import com.nttdata.nova.bookStore.entity.Editorial;
import com.nttdata.nova.bookStore.repository.IBookRepository;
import com.nttdata.nova.bookStore.service.IBookService;

@SpringBootTest()
@EnableCaching
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_CLASS)
//@Sql("/initDB.sql")
public class BookServiceCacheTest {
	@Autowired
	private IBookService bookService;
	
	@MockBean
	private IBookRepository bookRepository;
	
	private Long id = 1L;


	@Test
	public void getAllBooks() {
		bookService.getAllBooks();
		bookService.getAllBooks();

		Mockito.verify(bookRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void getBookByIdTest() {
		bookService.getBookById(id);
		bookService.getBookById(id);

		Mockito.verify(bookRepository, Mockito.times(1)).findById(id);
	}
	
	@Test
	public void getBookByTitleTest() {		
		bookService.getBookByTitle("Segundo libro");
		bookService.getBookByTitle("Segundo libro");

		Mockito.verify(bookRepository, Mockito.times(1)).findByTitleIs("Segundo libro");
	}
	
	@Test
	public void getBooksFromEditorialTest() {
		
		Editorial testEditorial = new Editorial(21L, "Primera Editorial");
		EditorialDTOJsonRequestExtended testEditorialDTO = new EditorialDTOJsonRequestExtended(testEditorial);
		
		bookService.getBooksFromEditorial(testEditorialDTO);
		bookService.getBooksFromEditorial(testEditorialDTO);

		Mockito.verify(bookRepository, Mockito.times(1)).findByEditorialIs(BDDMockito.any(Editorial.class));
	}
}

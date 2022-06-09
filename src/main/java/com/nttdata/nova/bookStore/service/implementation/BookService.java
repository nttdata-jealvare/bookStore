package com.nttdata.nova.bookStore.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.nttdata.nova.bookStore.dto.BookDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.BookDTOJsonRequestExtended;
import com.nttdata.nova.bookStore.dto.BookDTOJsonResponse;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequestExtended;
import com.nttdata.nova.bookStore.entity.Book;
import com.nttdata.nova.bookStore.entity.Editorial;
import com.nttdata.nova.bookStore.repository.IBookRepository;
import com.nttdata.nova.bookStore.service.IBookService;

@Service
public class BookService implements IBookService{

	@Autowired
	IBookRepository bookRepository;

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Cacheable(value="books")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public Boolean checkBookExists(Long id) {
		Optional<Book> idBook = this.bookRepository.findById(id);

		return idBook.isPresent();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Cacheable(value="books")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public BookDTOJsonResponse getBookById(Long id) {
		Optional<Book> idBook = this.bookRepository.findById(id);

		return idBook.isPresent() ? new BookDTOJsonResponse(idBook.get()) : null;
	}

	/**
	 * 
	 */
	@Override
	@Cacheable(value="books")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public List<BookDTOJsonResponse> getAllBooks() {
		List<Book> response = (List<Book>) this.bookRepository.findAll();
		List<BookDTOJsonResponse> responseDTO = new ArrayList<BookDTOJsonResponse>();
		for(Book b: response) responseDTO.add(new BookDTOJsonResponse(b)); 
		
		return responseDTO;
	}

	/**
	 * 
	 * @param id
	 */
	@Override
	@CacheEvict(value="books", allEntries=true)
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteById(Long id) {
		this.bookRepository.deleteById(id);
	}

	/**
	 * 
	 */
	@Override
	@CacheEvict(value="books", allEntries=true)
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteAll() {
		this.bookRepository.deleteAll();
	}

	/**
	 * 
	 * @param inBook
	 * @return
	 */
	@Override
	@CacheEvict(value="books", allEntries=true)
	@PreAuthorize("hasRole('ADMIN')")
	public BookDTOJsonResponse create(BookDTOJsonRequest inBook) {
		Book response = this.bookRepository.save(new Book(inBook));		
		return new BookDTOJsonResponse(response);
	}

	/**
	 * 
	 * @param inBook
	 * @return
	 */
	@Override
	@CacheEvict(value="books", allEntries=true)
	@PreAuthorize("hasRole('ADMIN')")
	public BookDTOJsonResponse update(BookDTOJsonRequestExtended inBook) {
		if (!checkBookExists(inBook.getId()))
			return null;
		
		Book response = this.bookRepository.save(new Book(inBook));
		
		return new BookDTOJsonResponse(response);
	}

	// New functionality

	/**
	 * 
	 * @param title
	 * @return
	 */
	@Override
	@Cacheable(value="books")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public BookDTOJsonResponse getBookByTitle(String title) {
		
		Optional<Book> titleBook = this.bookRepository.findByTitleIs(title);
				
		return titleBook.isPresent() ?  new BookDTOJsonResponse(titleBook.get()) :  new BookDTOJsonResponse();
	}

	/**
	 * 
	 * @param editorial
	 * @return
	 */
	@Override
	@Cacheable(value="books")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public List<BookDTOJsonResponse> getBooksFromEditorial(EditorialDTOJsonRequestExtended editorial) {
		
		List<Book> response = (List<Book>)this.bookRepository.findByEditorialIs(new Editorial(editorial.getId(), editorial.getName()));
		List<BookDTOJsonResponse> responseDTO = new ArrayList<BookDTOJsonResponse>();
		for(Book b: response) responseDTO.add(new BookDTOJsonResponse(b)); 
		
		return responseDTO;
	}

}

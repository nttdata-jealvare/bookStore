package com.nttdata.nova.bookStore.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.nova.bookStore.dto.BookDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.BookDTOJsonResponse;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonResponse;
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
	public BookDTOJsonResponse getBookById(Long id) {
		Optional<Book> idBook = this.bookRepository.findById(id);

		return idBook.isPresent() ? new BookDTOJsonResponse(idBook.get()) : null;
	}

	/**
	 * 
	 */
	@Override
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
	public void deleteById(Long id) {
		this.bookRepository.deleteById(id);
	}

	/**
	 * 
	 */
	@Override
	public void deleteAll() {
		this.bookRepository.deleteAll();
	}

	/**
	 * 
	 * @param inBook
	 * @return
	 */
	@Override
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
	public BookDTOJsonResponse update(BookDTOJsonResponse inBook) {
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
	public BookDTOJsonResponse getBookByTitle(String title) {
		
		Book titleBook = this.bookRepository.findByTitleIs(title);
		return new BookDTOJsonResponse(titleBook);
	}

	/**
	 * 
	 * @param editorial
	 * @return
	 */
	@Override
	public List<BookDTOJsonResponse> getBooksFromEditorial(EditorialDTOJsonResponse editorial) {
		
		List<Book> response = this.bookRepository.findByEditorialIs(new Editorial(editorial.getId(), editorial.getName()));
		List<BookDTOJsonResponse> responseDTO = new ArrayList<BookDTOJsonResponse>();
		for(Book b: response) responseDTO.add(new BookDTOJsonResponse(b)); 
		
		return responseDTO;
	}

}

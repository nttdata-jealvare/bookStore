package com.nttdata.nova.bookStore.service;

import java.util.List;

import com.nttdata.nova.bookStore.dto.BookDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.BookDTOJsonResponse;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonResponse;

public interface IBookService {
	
	public Boolean checkBookExists(Long id);
	
	public BookDTOJsonResponse getBookById(Long id);
	public List<BookDTOJsonResponse> getAllBooks();
	
	public void deleteById(Long id);
	public void deleteAll();
	
	public BookDTOJsonResponse create(BookDTOJsonRequest inBook);
	public BookDTOJsonResponse update(BookDTOJsonResponse inBook);
	
	public BookDTOJsonResponse getBookByTitle(String title);
	public List<BookDTOJsonResponse> getBooksFromEditorial(EditorialDTOJsonResponse editorial);
}

package com.nttdata.nova.bookStore.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.nova.bookStore.service.IBookRegistryService;
import com.nttdata.nova.bookStore.service.IBookService;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.nttdata.nova.bookStore.controller.msg.MongoOperation;
import com.nttdata.nova.bookStore.dto.BookDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.BookDTOJsonRequestExtended;
import com.nttdata.nova.bookStore.dto.BookDTOJsonResponse;
import com.nttdata.nova.bookStore.dto.BookRegistryDTOJson;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequestExtended;
import com.nttdata.nova.bookStore.exception.InvalidIDException;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private IBookService bookService;

	@Autowired
	private IBookRegistryService bookRegistryService;
	
	/**
	 * HATEOAS
	 * 
	 * @param book
	 */
	public void addLinksIntoResponse(BookDTOJsonResponse book) {
		book.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getABook(book.getId()))
				.withSelfRel());
		book.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getABookTitle(book.getTitle()))
				.withRel("title"));
		if(book.getEditorial() != null) {
			book.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EditorialController.class).getEditorialById(book.getEditorial().getId()))
				.withRel("editorial"));
		}
	}

	
	@GetMapping("/books")
	@Operation(summary = "Get all books if there're any stored")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the books", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	public HttpEntity<List<BookDTOJsonResponse>> getBooks() {
		List<BookDTOJsonResponse> books = this.bookService.getAllBooks();

		for (BookDTOJsonResponse b : books) {
			addLinksIntoResponse(b);
		}

		this.bookRegistryService.save(new BookRegistryDTOJson(MongoOperation.FIND_ALL.toString(), new Date()));

		return new ResponseEntity<List<BookDTOJsonResponse>>(books, HttpStatus.OK);
	}

	@GetMapping("/book/{id}")
	@Operation(summary = "Get a book by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the book", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content),
			@ApiResponse(responseCode = "406", description = "Invalid ID", content = @Content) })
	public HttpEntity<BookDTOJsonResponse> getABook(@PathVariable Long id) {
		if (id <= 0) {
			throw new InvalidIDException();
		}
		BookDTOJsonResponse response = this.bookService.getBookById(id);
		try {
			if(response != null) {
				addLinksIntoResponse(response);
			}

			this.bookRegistryService
					.save(new BookRegistryDTOJson(MongoOperation.FIND_BY_ID.toString() + response.getId(), new Date()));

			return new ResponseEntity<BookDTOJsonResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<BookDTOJsonResponse>(response, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/bookTitle/{title}")
	@Operation(summary = "Get a book by title")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the book", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
	public HttpEntity<BookDTOJsonResponse> getABookTitle(@PathVariable String title) {

		BookDTOJsonResponse response = this.bookService.getBookByTitle(title);
		addLinksIntoResponse(response);

		this.bookRegistryService
				.save(new BookRegistryDTOJson(MongoOperation.FIND_ONE.toString() + "TITLE = " + title, new Date()));

		return new ResponseEntity<BookDTOJsonResponse>(response, HttpStatus.OK);
	}

	@PostMapping("/bookEditorial")
	@Operation(summary = "Get a book from editorial")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the books", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
	public HttpEntity<List<BookDTOJsonResponse>> getABookEditorial(@Valid
			@RequestBody EditorialDTOJsonRequestExtended editorial) {	
		// TODO
		List<BookDTOJsonResponse> books = this.bookService.getBooksFromEditorial(editorial);

		for (BookDTOJsonResponse b : books) {
			addLinksIntoResponse(b);
		}

		this.bookRegistryService.save(new BookRegistryDTOJson(
				MongoOperation.FIND_BY.toString() + "EDITORIAL_ID = " + editorial.getId(), new Date()));

		return new ResponseEntity<List<BookDTOJsonResponse>>(books, HttpStatus.OK);
	}

	@DeleteMapping("/book/{id}")
	@Operation(summary = "Delete all books")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Delete the book succesfully") })
	public HttpEntity<String> deleteABook(@PathVariable Long id) {
		this.bookService.deleteById(id);

		this.bookRegistryService.save(new BookRegistryDTOJson(MongoOperation.DELETE.toString() + id, new Date()));

		return new ResponseEntity<String>("Delete the book succesfully", HttpStatus.OK);
	}

	@DeleteMapping("/books")
	@Operation(summary = "Delete all books")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Delete all books") })
	public HttpEntity<String> deleteAllBooks() {
		this.bookService.deleteAll();

		this.bookRegistryService.save(new BookRegistryDTOJson(MongoOperation.DELETE_ALL.toString(), new Date()));

		return new ResponseEntity<String>("Delete all books", HttpStatus.OK);
	}

	@PostMapping("/book")
	@Operation(summary = "Add a book")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Book added", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
	public HttpEntity<BookDTOJsonResponse> createABook(@RequestBody BookDTOJsonRequest book) {
		BookDTOJsonResponse response = this.bookService.create(book);
		try {
			if(response != null) {
				addLinksIntoResponse(response);
			}

			this.bookRegistryService
					.save(new BookRegistryDTOJson(MongoOperation.CREATE.toString() + response.getId(), new Date()));

			return new ResponseEntity<BookDTOJsonResponse>(response, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<BookDTOJsonResponse>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/book")
	@Operation(summary = "Update a book")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Book udpated", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
	public HttpEntity<BookDTOJsonResponse> updateABook(@RequestBody BookDTOJsonRequestExtended book) {
		BookDTOJsonResponse response = this.bookService.update(book);
		addLinksIntoResponse(response);

		this.bookRegistryService
				.save(new BookRegistryDTOJson(MongoOperation.UPDATE.toString() + response.getId(), new Date()));

		return new ResponseEntity<BookDTOJsonResponse>(response, HttpStatus.OK);
	}

}

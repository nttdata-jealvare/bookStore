package com.nttdata.nova.bookStore.controller;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nttdata.nova.bookStore.dto.BookDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequestExtended;
import com.nttdata.nova.bookStore.entity.Book;
import com.nttdata.nova.bookStore.entity.Editorial;
import com.nttdata.nova.bookStore.service.IBookRegistryService;
import com.nttdata.nova.bookStore.service.implementation.BookService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;
	
	@MockBean
	private IBookRegistryService bookRegistryService;

	/**
	 * External object used to convert Task objects into JSON objects
	 */
	@Autowired
	private ObjectMapper objectMapper;

	private BookDTOJsonRequest bookRequest;
	private EditorialDTOJsonRequestExtended editorialResponse;

	/**
	 * Mapper initializer
	 */
	@TestConfiguration
	static class TestConfigurationApp {
		@Bean
		ObjectMapper objectMapperPrettyPrinting() {
			return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		}
	}

	@BeforeEach
	public void initialize() {
		this.editorialResponse = new EditorialDTOJsonRequestExtended((long) 21, "Primera Editorial");
		this.bookRequest = new BookDTOJsonRequest(new Book("Test", "Author test", new Date(), 74, "Description test",
				new Editorial((long) 21, "Primera Editorial")));
	}

	/**
	 * Add a book
	 * 
	 * @throws Exception
	 */
	@Test
	public void addABook() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders
				.post("/book/book")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(this.bookRequest));

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
	}

	/**
	 * Get all books
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllBooks() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/book/books").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	/**
	 * Get a book by ID
	 * 
	 * @throws Exception
	 */
	@Test
	public void getABook() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/book/book/{id}", 1)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	/**
	 * Get a book by Editorial
	 * 
	 * @throws Exception
	 */
	@Test
	public void getBooksByEditorial() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/book/bookEditorial")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(this.editorialResponse));

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	/**
	 * Delete all books
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteBooks() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/book/books");

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

	}
}

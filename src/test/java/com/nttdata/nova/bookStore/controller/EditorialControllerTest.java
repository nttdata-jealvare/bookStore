package com.nttdata.nova.bookStore.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonResponse;
import com.nttdata.nova.bookStore.entity.Editorial;
import com.nttdata.nova.bookStore.service.IBookService;
import com.nttdata.nova.bookStore.service.IEditorialService;
import com.nttdata.nova.bookStore.service.implementation.EditorialService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EditorialController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class EditorialControllerTest {

	@Autowired
	private MockMvc mockMvc;

	/*@MockBean
	private IBookService bookService;*/

	@MockBean
	private IEditorialService editorialService;
	

	/**
	 * External object used to convert Task objects into JSON objects
	 */
	@Autowired
	private ObjectMapper objectMapper;

	private EditorialDTOJsonRequest editorialRequest;

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
		this.editorialRequest = new EditorialDTOJsonRequest(new Editorial("Test Editorial"));
	}

	/**
	 * Add a new editorial
	 * 
	 * @throws Exception
	 */
	@Test
	public void addAEditorial() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/editorial/editorial")
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"name\": \"Nova editions\"}")
				//.content(objectMapper.writeValueAsBytes(this.editorialRequest))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
	}

	/**
	 * Get all books
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAllEditorials() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/editorial/editorials").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	/**
	 * Get a editorial by ID
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAEditorialNotFound() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/editorial/editorial/{id}", 88)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
	}

	/**
	 * Get a editorial by name
	 * 
	 * @throws Exception
	 */
	@Test
	public void getAEditorialByName() throws Exception {
		EditorialDTOJsonResponse editorial = new EditorialDTOJsonResponse();
		editorial.setId(Long.valueOf(1));
		editorial.setName("Test");
		
		BDDMockito.given(editorialService.getEditorialById(Long.valueOf(1))).willReturn(editorial);
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/editorial/editorialName/{name}", "Test")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	/**
	 * Delete all editorials
	 * 
	 * @throws Exception
	 */
	@Test
	public void deleteEditorials() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/editorial/editorials");

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	/*@AfterEach
	public void reset_mocks() {
		Mockito.reset(editorialService);
		//Mockito.reset(bookService);
	}*/

}

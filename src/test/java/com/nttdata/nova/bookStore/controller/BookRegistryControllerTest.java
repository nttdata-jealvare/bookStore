package com.nttdata.nova.bookStore.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nttdata.nova.bookStore.service.implementation.BookRegistryService;

@WebMvcTest(controllers = RegistryController.class)
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class BookRegistryControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookRegistryService bookRegistryService;
	
	@Test
	public void getAllRegistries() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/registry/allRegistries").accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
}

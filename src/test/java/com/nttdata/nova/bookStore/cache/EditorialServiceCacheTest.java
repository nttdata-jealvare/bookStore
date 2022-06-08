package com.nttdata.nova.bookStore.cache;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import com.nttdata.nova.bookStore.repository.IEditorialRepository;
import com.nttdata.nova.bookStore.service.IEditorialService;

@SpringBootTest()
@EnableCaching
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_CLASS)
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class EditorialServiceCacheTest {
	
	@Autowired
	private IEditorialService editorialService;
	
	@MockBean
	private IEditorialRepository editorialRepository;

	private Long id = 21L;
	private String name = "Primera Editorial";

	@Test
	public void getAllEditorials() {
		editorialService.getAllEditorials();
		editorialService.getAllEditorials();

		Mockito.verify(editorialRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void getEditorialByID() {
		editorialService.getEditorialById(id);
		editorialService.getEditorialById(id);

		Mockito.verify(editorialRepository, Mockito.times(1)).findById(id);
	}
	
	@Test
	public void getEditorialByName() {
		editorialService.getEditorialByName(name);
		editorialService.getEditorialByName(name);

		Mockito.verify(editorialRepository, Mockito.times(1)).findByNameIs(name);
	}
}

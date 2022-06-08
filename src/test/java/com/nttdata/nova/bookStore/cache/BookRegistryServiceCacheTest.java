package com.nttdata.nova.bookStore.cache;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.nova.bookStore.repository.IBookRegistryRepository;

import com.nttdata.nova.bookStore.service.IBookRegistryService;

@SpringBootTest()
@EnableCaching
@DirtiesContext(classMode=DirtiesContext.ClassMode.AFTER_CLASS)
@WithMockUser(username = "admin", roles = { "ADMIN" })
public class BookRegistryServiceCacheTest {

	@Autowired
	private IBookRegistryService bookRegistryService;
	
	@MockBean
	private IBookRegistryRepository bookRegistryRepository;
	
	@Test
	public void getAll() {
		bookRegistryService.getAll();
		bookRegistryService.getAll();
		
		Mockito.verify(bookRegistryRepository, Mockito.times(1)).findAll();
	}
}

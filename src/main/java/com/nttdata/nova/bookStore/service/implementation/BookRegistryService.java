package com.nttdata.nova.bookStore.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nttdata.nova.bookStore.collection.BookRegistry;
import com.nttdata.nova.bookStore.dto.BookRegistryDTOJson;
import com.nttdata.nova.bookStore.repository.IBookRegistryRepository;
import com.nttdata.nova.bookStore.service.IBookRegistryService;

@Service
public class BookRegistryService implements IBookRegistryService {
	
	@Autowired
	private IBookRegistryRepository bookRegistryRepository;

	@Override
	@CacheEvict(value="registries", allEntries=true)
	public BookRegistryDTOJson save(BookRegistryDTOJson bookReg) {
	
		bookReg.setId(Long.valueOf(this.bookRegistryRepository.findAll().size() + 1));
		
		BookRegistry response = this.bookRegistryRepository.save(new BookRegistry(bookReg));
		
		return new BookRegistryDTOJson(response);
	}

	@Override
	@Cacheable(value="registries")
	public List<BookRegistryDTOJson> getAll() {
		List<BookRegistryDTOJson> booksDTO = new ArrayList<BookRegistryDTOJson>();
		
		List<BookRegistry> books = (List<BookRegistry>) this.bookRegistryRepository.findAll();
		
		for(BookRegistry b : books) booksDTO.add(new BookRegistryDTOJson(b));
		
		return booksDTO;
	}

}

package com.nttdata.nova.bookStore.service;

import java.util.List;

import com.nttdata.nova.bookStore.dto.BookRegistryDTOJson;

public interface IBookRegistryService {
	
	public BookRegistryDTOJson save(BookRegistryDTOJson bookReg);
	public List<BookRegistryDTOJson> getAll();
	
}

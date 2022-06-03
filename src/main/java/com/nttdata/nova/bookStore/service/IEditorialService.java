package com.nttdata.nova.bookStore.service;

import java.util.List;

import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonResponse;

public interface IEditorialService {
	
	public Boolean checkEditorialExists(Long id);
	
	public EditorialDTOJsonResponse getEditorialById(Long id);
	public List<EditorialDTOJsonResponse> getAllEditorials();
	
	public void deleteById(Long id);
	public void deleteAll();
	
	public EditorialDTOJsonResponse create(EditorialDTOJsonRequest inEditorial);
	public EditorialDTOJsonResponse update(EditorialDTOJsonResponse inEditorial);
	
	public EditorialDTOJsonResponse getEditorialByName(String name);
}

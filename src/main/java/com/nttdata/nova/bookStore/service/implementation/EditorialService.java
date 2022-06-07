package com.nttdata.nova.bookStore.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequestExtended;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonResponse;
import com.nttdata.nova.bookStore.entity.Editorial;
import com.nttdata.nova.bookStore.repository.IEditorialRepository;
import com.nttdata.nova.bookStore.service.IEditorialService;

@Service
public class EditorialService implements IEditorialService{

	@Autowired
	IEditorialRepository editorialReposity;

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Cacheable(value="editorials")
	public Boolean checkEditorialExists(Long id) {
		Optional<Editorial> idEditorial = this.editorialReposity.findById(id);
		return idEditorial.isPresent();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Override
	@Cacheable(value="editorials")
	public EditorialDTOJsonResponse getEditorialById(Long id) {
		Optional<Editorial> idEditorial = this.editorialReposity.findById(id);

		return idEditorial.isPresent() ? new EditorialDTOJsonResponse(idEditorial.get()) : null;
	}

	/**
	 * 
	 */
	@Override
	@Cacheable(value="editorials")
	public List<EditorialDTOJsonResponse> getAllEditorials() {
		List<EditorialDTOJsonResponse> responseDTO = new ArrayList<EditorialDTOJsonResponse>();
		
		List<Editorial> response = (List<Editorial>) this.editorialReposity.findAll();
		
		for(Editorial e : response) {
			responseDTO.add(new EditorialDTOJsonResponse(e));
		}
		
		return responseDTO;
	}

	/**
	 * 
	 * @param id
	 */
	@Override
	@CacheEvict(value="editorials", allEntries=true)
	public void deleteById(Long id) {
		this.editorialReposity.deleteById(id);
	}

	/**
	 * 
	 */
	@Override
	@CacheEvict(value="editorials", allEntries=true)
	public void deleteAll() {
		this.editorialReposity.deleteAll();
	}

	/**
	 * 
	 * @param inEditorial
	 * @return
	 */
	@Override
	@CacheEvict(value="editorials", allEntries=true)
	public EditorialDTOJsonResponse create(EditorialDTOJsonRequest inEditorial) {
		Editorial response = this.editorialReposity.save(new Editorial(inEditorial.getName()));
		EditorialDTOJsonResponse responseDTO = new EditorialDTOJsonResponse(response);
		return responseDTO;
	}

	/**
	 * 
	 * @param inEditorial
	 * @return
	 */
	@Override
	@CacheEvict(value="editorials", allEntries=true)
	public EditorialDTOJsonResponse update(EditorialDTOJsonRequestExtended inEditorial) {
		if (!checkEditorialExists(inEditorial.getId()))
			return null;
		
		Editorial response = this.editorialReposity.save(new Editorial(inEditorial.getId(), inEditorial.getName()));
		EditorialDTOJsonResponse responseDTO = new EditorialDTOJsonResponse(response);
		
		return responseDTO;
	}
	
	// New functionality
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	@Override
	@Cacheable(value="editorials")
	public EditorialDTOJsonResponse getEditorialByName(String name) {
		Editorial response = this.editorialReposity.findByNameIs(name);
		EditorialDTOJsonResponse responseDTO = new EditorialDTOJsonResponse(response);
		
		return responseDTO;		
	}
	
}

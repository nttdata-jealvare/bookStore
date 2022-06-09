package com.nttdata.nova.bookStore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.nttdata.nova.bookStore.entity.Editorial;

@DataJpaTest
public class EditorialRepositoryTest {
	
	/**
	 * 
	 */
	@Autowired
	private IEditorialRepository editorialRepository;
	
	/**
	 * 
	 */
	private Editorial editorial;

	/**
	 * 
	 */
	private final Long id = 21L;
	private final String name = "Editorial Testing";
	
	/**
	 * 
	 */
	@BeforeEach
	public void initialize() {
		this.editorial = new Editorial(name);
	}
	
	/**
	 * 
	 */
	@Test
	public void addAEditorial() {
		Editorial insertedEditorial = this.editorialRepository.save(this.editorial);
		assertThat(insertedEditorial).isNotNull();
		assertThat(insertedEditorial.getName()).isEqualTo(this.editorial.getName());
	}
	
	/**
	 * 
	 */
	@Test
	public void getAllTheEditorials() {
		List<Editorial> editorials = (List<Editorial>) this.editorialRepository.findAll();
		assertThat(editorials).isNotNull(); // It should not be empty
	}
	
	/**
	 * 
	 */
	@Test
	public void getEditorialById() {
		List<Editorial> editorials = (List<Editorial>) this.editorialRepository.findAll();
		
		Optional<Editorial> editorialId = this.editorialRepository.findById(editorials.get(0).getId());
		assertThat(editorialId.isPresent()).isTrue();
		assertThat(editorialId.get().getId()).isEqualTo(editorials.get(0).getId());
	}
	
	/**
	 * 
	 */
	@Test
	public void getEditorialByName() {
		String auxName = "Primera Editorial";
		
		Optional<Editorial> editorial = this.editorialRepository.findByNameIs(auxName);
		assertThat(editorial.isPresent()).isTrue();
		assertThat(editorial.get().getName()).isEqualTo(auxName);
	}
	
	/**
	 * 
	 */
	@Test
	public void deleteAEditorial() {
		this.editorialRepository.deleteById(this.id);
		assertThat(this.editorialRepository.findById(this.id)).isEmpty();
	}

}

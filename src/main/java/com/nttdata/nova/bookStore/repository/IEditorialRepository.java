package com.nttdata.nova.bookStore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.nova.bookStore.entity.Editorial;

/**
 * 
 * @author jalvarco
 *
 */
@Repository
public interface IEditorialRepository extends CrudRepository<Editorial, Long>{
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Optional<Editorial> findByNameIs(String name);
}

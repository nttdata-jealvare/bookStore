package com.nttdata.nova.bookStore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.nova.bookStore.entity.Book;
import com.nttdata.nova.bookStore.entity.Editorial;

/**
 * 
 * @author jalvarco
 *
 */
@Repository
public interface IBookRepository extends CrudRepository<Book, Long> {

	/**
	 * 
	 * @param title
	 * @return
	 */
	public Optional<Book> findByTitleIs(String title);
	
	/**
	 * 
	 * @param editorial
	 * @return
	 */
	public List<Book> findByEditorialIs(Editorial editorial);
}

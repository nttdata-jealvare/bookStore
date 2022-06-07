package com.nttdata.nova.bookStore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.nova.bookStore.collection.BookRegistry;

@Repository
public interface IBookRegistryRepository extends MongoRepository<BookRegistry, String>{

}

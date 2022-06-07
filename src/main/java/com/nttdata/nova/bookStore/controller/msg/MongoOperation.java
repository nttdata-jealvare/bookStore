package com.nttdata.nova.bookStore.controller.msg;

/**
 * 
 * @author jalvarco
 *
 */
public enum MongoOperation {
	CREATE("CREATE book with id "),
	UPDATE("UPDATE book with id "),
	DELETE("DELETE book with id "),
	DELETE_ALL("DELETE_ALL books"),
	FIND_ALL("FIND_ALL books"),
	FIND_BY_ID("FIND_BY_ID book with id "),
	FIND_ONE("FIND_ONE book with "),
	FIND_BY("FIND_BY books by ");
	
	private String text;
	
	MongoOperation(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
	
}

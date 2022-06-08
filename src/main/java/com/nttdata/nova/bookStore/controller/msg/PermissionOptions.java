package com.nttdata.nova.bookStore.controller.msg;

/**
 * 
 * @author jalvarco
 *
 */
public enum PermissionOptions {
	ADMIN("hasRole('ADMIN')"),
	ALL("hasRole('ADMIN') or hasRole('USER')");
	
	private final String text;
	
	PermissionOptions(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}

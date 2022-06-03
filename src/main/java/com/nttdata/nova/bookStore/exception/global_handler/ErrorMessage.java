package com.nttdata.nova.bookStore.exception.global_handler;

import java.util.Date;

public class ErrorMessage {
	private int errorCode;

	private Date timestamp;

	private String message;

	private String description;

	public ErrorMessage(int errorCode, Date timestamp, String message, String description) {
		this.errorCode = errorCode;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

package br.charles.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class ExceptionResponse {
	private Date timestamp;
	private HttpStatus status;
	private String message;
	private String details;
	
	
	public ExceptionResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	public ExceptionResponse(Date timestamp, HttpStatus status, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.details = details;
	}

	
	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public HttpStatus getStatus() {
		return status;
	}
	


}
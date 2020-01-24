package br.charles.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContatoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	public ContatoNotFoundException(String message) {
		super(message);
	}
}
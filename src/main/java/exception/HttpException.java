package exception;

import webserver.HttpStatus;

public class HttpException extends RuntimeException {

	private HttpStatus httpStatus;
	public HttpException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}


}

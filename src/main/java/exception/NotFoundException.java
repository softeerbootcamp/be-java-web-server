package exception;

import webserver.HttpStatus;

public class NotFoundException extends HttpException {
	public NotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}
}

package exception;

import webserver.HttpStatus;

public class InternalServerErrorException extends HttpException {
	public InternalServerErrorException(String message) {
		super(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

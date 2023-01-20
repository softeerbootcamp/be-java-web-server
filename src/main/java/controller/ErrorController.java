package controller;

import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import webserver.HttpStatus;

public class ErrorController extends AbstractController {

	private static final String PATH = "/error";

	private static ErrorController instance;

	public static ErrorController getInstance() {
		if (instance == null) {
			synchronized (ErrorController.class) {
				instance = new ErrorController();
			}
		}
		return instance;
	}
	public void service(HttpRequest httpRequest, HttpResponse httpResponse, Exception e) {
		httpResponse.setHttpResponse(HttpStatus.OK,e.getMessage(), ContentType.HTML);
	}
}

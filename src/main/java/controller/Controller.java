package controller;

import java.io.IOException;

import request.HttpRequest;
import response.HttpResponse;

public interface Controller {

	public HttpResponse service(HttpRequest httpRequest) throws IOException;
}

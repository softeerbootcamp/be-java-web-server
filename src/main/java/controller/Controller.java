package controller;

import java.io.IOException;

import request.HttpRequest;
import response.HttpResponse;

public interface Controller {

	public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException;
}

package controller;

import java.io.IOException;

import response.ContentType;
import webserver.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;
import webserver.HttpStatus;

public class AbstractController implements Controller {

	@Override
	public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		if (httpRequest.getMethod().equals(HttpMethod.GET)) {
			doGet(httpRequest, httpResponse);
			return;
		}
		doPost(httpRequest, httpResponse);
	}

	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

		httpResponse.setHttpResponse(HttpStatus.OK, "500 error", ContentType.HTML); // 500내려주기
	}

	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		httpResponse.setHttpResponse(HttpStatus.OK, "500 error", ContentType.HTML); // 500내려주기 // 500내려주기
	}

}

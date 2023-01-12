package controller;

import java.io.IOException;

import response.ContentType;
import webserver.HttpMethod;
import request.HttpRequest;
import response.HttpResponse;
import webserver.HttpStatus;

public class AbstractController implements Controller {

	@Override
	public HttpResponse service(HttpRequest httpRequest) throws IOException {
		if (httpRequest.getMethod().equals(HttpMethod.GET)) {
			return doGet(httpRequest);
		}
		return doPost(httpRequest);
	}

	public HttpResponse doGet(HttpRequest httpRequest) throws IOException {

		//File file = new File("./webapp" + httpRequest.getUrl().getPath());
		return new HttpResponse(HttpStatus.OK, "500 error", ContentType.HTML); // 500내려주기
	}

	public HttpResponse doPost(HttpRequest httpRequest) {
		return new HttpResponse(HttpStatus.OK, "500 error", ContentType.HTML); // 500내려주기 // 500내려주기
	}

}

package controller;

import webserver.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class AbstractController implements Controller{

	@Override
	public HttpResponse service(HttpRequest httpRequest) {
		if (httpRequest.getMethod().equals(HttpMethod.GET)) {
			return doGet(httpRequest);
		}
		return doPost(httpRequest);
	}

	public HttpResponse doGet(HttpRequest httpRequest) {
		return new HttpResponse() // 500내려주기
	}

	public HttpResponse doPost(HttpRequest httpRequest) {
		return new HttpResponse() // 500내려주기
	}

}

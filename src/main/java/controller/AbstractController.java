package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
		defaultErrorPage(httpResponse);
	}

	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		defaultErrorPage(httpResponse);
	}

	private void defaultErrorPage(HttpResponse httpResponse) throws IOException {
		File file = new File("./webapp/400.html");
		httpResponse.setHttpResponse(HttpStatus.OK, new String(Files.readAllBytes(file.toPath())), ContentType.HTML); // 500내려주기
	}

}

package controller;

import db.Database;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import webserver.HttpStatus;
import webserver.Url;

public class UserController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		Url url = httpRequest.getUrl();
		User user = User.of(url.getQuery("userId"), url.getQuery("password"), url.getQuery("name"),
			url.getQuery("email"));
		Database.addUser(user);
		logger.info(user + " 회원가입했습니다.");
		// redirect
		httpResponse.setHttpResponse(HttpStatus.FOUND,
			"", ContentType.HTML,
			"/index.html");
	}

	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		User user = User.of(httpRequest.getRequestBody("userId"), httpRequest.getRequestBody("password"), httpRequest.getRequestBody("name"),
			httpRequest.getRequestBody("email"));
		Database.addUser(user);
		logger.info(user + " 회원가입했습니다.");
		// refactoring
		// redirect
		httpResponse.setHttpResponse(HttpStatus.FOUND,
			"", ContentType.HTML,
			"/index.html");
	}

}

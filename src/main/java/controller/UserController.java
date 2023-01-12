package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.Database;
import model.User;
import response.ContentType;
import request.HttpRequest;
import response.HttpResponse;
import webserver.HttpStatus;
import webserver.Url;

public class UserController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
		Url url = httpRequest.getUrl();
		User user = User.of(url.getQuery("userId"), url.getQuery("password"), url.getQuery("name"),
			url.getQuery("email"));
		logger.info(user + " 회원가입했습니다.");

		Database.addUser(user);
		File file = new File("./webapp" + httpRequest.getUrl().getPath());
		return new HttpResponse(HttpStatus.OK, new String(Files.readAllBytes(file.toPath())), ContentType.HTML,
			"/index.html");
	}

}

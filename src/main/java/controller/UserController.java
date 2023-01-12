package controller;

import java.util.Map;

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.Url;

public class UserController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public HttpResponse doGet(HttpRequest httpRequest) {
		Url url = httpRequest.getUrl();
		User user = User.of(url.getQuery("userId"), url.getQuery("password"), url.getQuery("name"), url.getQuery("email"));
		logger.info(user + " 회원가입했습니다.");
		Database.addUser(user);
		return new HttpResponse() // TODO
	}

}

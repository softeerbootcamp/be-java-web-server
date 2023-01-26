package controller;

import java.io.IOException;

import exception.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import service.UserService;
import webserver.HttpStatus;

public class UserController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	public static final String PATH = "/user/create";
	private static UserController instance;
	private final UserService userService;

	public static UserController getInstance() {
		if (instance == null) {
			synchronized (UserController.class) {
				instance = new UserController();
			}
		}
		return instance;
	}

	public UserController() {
		this.userService = UserService.getInstance();
	}

	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		// TODO parameter 예외처리 어떻게 할지 고민중...
		if (userService.idExist(httpRequest.getRequestBody("userId"))) {
			throw new BadRequestException("이미 존재하는 아이디입니다.");
		}
		userService.addUser(httpRequest.getRequestBody("userId"), httpRequest.getRequestBody("password"),
			httpRequest.getRequestBody("name"),
			httpRequest.getRequestBody("email"));

		httpResponse.setHttpResponse(HttpStatus.FOUND,
			"", ContentType.HTML,
			"/index.html");
	}

}

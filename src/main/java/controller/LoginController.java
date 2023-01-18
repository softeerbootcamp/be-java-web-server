package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import service.UserService;
import session.Session;
import session.SessionManager;
import webserver.HttpStatus;

public class LoginController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	public static final String PATH = "/user/login";

	private static LoginController instance;

	private final UserService userService;


	public static LoginController getInstance() {
		if (instance == null) {
			synchronized (LoginController.class) {
				instance = new LoginController();
			}
		}
		return instance;
	}

	public LoginController() {
		this.userService = UserService.getInstance();
	}

	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		if (userService.matchIdPassword(httpRequest.getRequestBody("userId"), httpRequest.getRequestBody("password"))) {
			User user = userService.findByUserId(httpRequest.getRequestBody("userId"));
			Session session = new Session();
			session.setAttribuite("user", user);
			// TODO 세션 적용해야함
			httpResponse.setHttpResponse(HttpStatus.FOUND, "", ContentType.HTML, "/index.html");
		} else {
			httpResponse.setHttpResponse(HttpStatus.FOUND, "", ContentType.HTML, "/user/login_failed.html");
		}
	}

}

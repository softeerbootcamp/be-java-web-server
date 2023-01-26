package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.User;
import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import service.UserService;
import view.Model;
import view.UserListView;
import webserver.HttpStatus;

public class UserListController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

	public static String PATH = "/user/list";

	private static UserListController instance;

	private final UserService userService;

	private final UserListView userListView;

	public static UserListController getInstance() {
		if (instance == null) {
			synchronized (UserListController.class) {
				instance = new UserListController();
			}
		}
		return instance;
	}

	private UserListController() {
		this.userService = UserService.getInstance();
		this.userListView = UserListView.getInstance();
	}

	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

		if (httpRequest.getSessionUser().equals(User.GUEST)) {
			httpResponse.redirect("/user/login.html");
			return;
		}

		Model model = new Model();
		List<User> users = userService.findAll();
		model.addModel("users", users);

		// rendering
		// TODO View 다형성을 잘 못사용하고 있는데 고민중...
		userListView.makeView(httpRequest, httpResponse, model);
	}

}

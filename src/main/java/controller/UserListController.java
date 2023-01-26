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
import webserver.HttpStatus;

public class UserListController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

	public static String PATH = "/user/list";

	private static UserListController instance;

	private final UserService userService;

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
	}

	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		if (httpRequest.getSessionUser().equals(User.GUEST)) {
			httpResponse.redirect("/user/login.html");
			return;
		}

		// rendering
		File file = new File("./webapp/user/list.html");
		String page = new String(Files.readAllBytes(file.toPath()));
		StringBuilder userList = new StringBuilder();
		List<User> users = userService.findAll();
		for (int i = 0; i < users.size(); i++) {
			userList.append("<tr>");
			userList.append("<th scope=\"row\">");
			userList.append(i + 1);
			userList.append("</th> <td>");
			userList.append(users.get(i).getUserId());
			userList.append(" </td> <td>");
			userList.append(users.get(i).getName());
			userList.append(" </td> <td>");
			userList.append(users.get(i).getEmail());
			userList.append("</td><td>");
			if (httpRequest.getSessionUser().getUserId().equals(users.get(i).getUserId())) {
				userList.append("<a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a>");
			}
			userList.append("</td></tr>");
		}

		page = page.replace("<!-- userList -->", userList);
		httpResponse.setHttpResponse(HttpStatus.OK, page, ContentType.HTML);
	}

}

package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import model.User;
import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import webserver.HttpStatus;

public class UserListView implements View {

	private static UserListView instance;

	public static UserListView getInstance() {
		if (instance == null) {
			synchronized (UserListView.class) {
				instance = new UserListView();
			}
		}
		return instance;
	}
	@Override
	public void render(HttpRequest httpRequest, HttpResponse httpResponse, Model model, String menubar) throws
		IOException {
		List<User> users = ArrayList.class.cast(model.getValue("users"));
		File file = new File("./webapp/user/list.html");
		String page = new String(Files.readAllBytes(file.toPath()));
		StringBuilder userList = new StringBuilder();

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
		page = page.replace("<!-- menubar -->", menubar);
		httpResponse.setHttpResponse(HttpStatus.OK, page, ContentType.HTML);
	}
}

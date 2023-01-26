package view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Board;
import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import webserver.HttpStatus;

public class HomeView implements View {

	private static HomeView instance;

	public static HomeView getInstance() {
		if (instance == null) {
			synchronized (HomeView.class) {
				instance = new HomeView();
			}
		}
		return instance;
	}

	@Override
	public void render(HttpRequest httpRequest, HttpResponse httpResponse, Model model, String menubar) throws IOException {
		// ArrayList<User> userList = ArrayList.class.cast(userObjList);
		List<Board> boards = ArrayList.class.cast(model.getValue("boards")); // 이게 되나?...
		File file = new File("./webapp/index.html");
		String page = new String(Files.readAllBytes(file.toPath()));
		StringBuilder boardList = new StringBuilder();

		for (int i = 0; i < boards.size(); i++) {
			boardList.append("<li>\n"
				+ "                  <div class=\"wrap\">\n"
				+ "                      <div class=\"main\">\n"
				+ "                          <strong class=\"subject\">\n"
				+ "                              <a href=\"qnahow.html\">");
			boardList.append(boards.get(i).getTitle());
			boardList.append("</a>\n"
				+ "                          </strong>\n"
				+ "                          <div class=\"auth-info\">\n"
				+ "                              <i class=\"icon-add-comment\"></i>\n"
				+ "                              <span class=\"time\">");
			boardList.append(boards.get(i).getTime());
			boardList.append("</span>\n"
				+ "                              <a href=\"userrofile.html\" class=\"author\">");
			boardList.append(boards.get(i).getWriter());
			boardList.append("<div class=\"reply\" title=\"댓글\">\n"
				+ "                              <i class=\"icon-reply\"></i>\n"
				+ "                              <span class=\"point\">");
			boardList.append(boards.get(i).getId());
			boardList.append("</span>\n"
				+ "                          </div>\n"
				+ "                      </div>\n"
				+ "                  </div>");
		}
		page = page.replace("<!-- board list -->", boardList);
		page = page.replace("<!-- menubar -->", menubar);
		httpResponse.setHttpResponse(HttpStatus.OK, page, ContentType.HTML);
	}
}

package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Board;
import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import service.BoardService;
import webserver.HttpStatus;

public class HomeController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	public static final String PATH = "/home";

	private static HomeController instance;

	private final BoardService boardService;

	public static HomeController getInstance() {
		if (instance == null) {
			synchronized (HomeController.class) {
				instance = new HomeController();
			}
		}
		return instance;
	}

	public HomeController() {
		this.boardService = BoardService.getInstance();
	}

	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		List<Board> boards = boardService.findBoards();
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
		httpResponse.setHttpResponse(HttpStatus.OK, page, ContentType.HTML);
	}
}

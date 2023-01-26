package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repository.BoardRepository;
import repository.BoardRepositoryMysql;
import request.HttpRequest;
import response.ContentType;
import response.HttpResponse;
import service.BoardService;
import webserver.HttpStatus;

public class BoardController extends AbstractController {

	private final BoardService boardService;

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	public static final String PATH = "/board";

	private static BoardController instance;

	public static BoardController getInstance() {
		if (instance == null) {
			synchronized (BoardController.class) {
				instance = new BoardController();
			}
		}
		return instance;
	}

	public BoardController() {
		this.boardService = BoardService.getInstance();
	}

	public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
		boardService.addUser(httpRequest.getRequestBody("title"), httpRequest.getRequestBody("contents"),
			httpRequest.getRequestBody("writer"));

		httpResponse.redirect("/home");
	}

}

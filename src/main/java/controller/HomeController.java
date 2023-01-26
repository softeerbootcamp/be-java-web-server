package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Board;
import request.HttpRequest;
import response.HttpResponse;
import service.BoardService;
import view.HomeView;
import view.Model;

public class HomeController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	public static final String PATH = "/home";

	private static HomeController instance;

	private final BoardService boardService;

	private final HomeView homeView;

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
		this.homeView = HomeView.getInstance();
	}

	public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		Model model = new Model();
		List<Board> boards = boardService.findBoards();
		model.addModel("boards", boards);
		homeView.makeView(httpRequest, httpResponse, model);
	}
}

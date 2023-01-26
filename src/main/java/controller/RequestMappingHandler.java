package controller;

import java.util.Arrays;
import java.util.regex.Pattern;

import exception.BadRequestException;
import request.HttpRequest;

public enum RequestMappingHandler {

	USER_CONTROLLER(UserController.PATH, UserController.getInstance()),
	LOGIN_CONTROLLER(LoginController.PATH, LoginController.getInstance()),
	USER_LIST_CONTROLLER(UserListController.PATH, UserListController.getInstance()),
	BOARD_CONTROLLER(BoardController.PATH, BoardController.getInstance()),
	HOME_CONTROLLER(HomeController.PATH, HomeController.getInstance()),;
	private String path;
	private Controller controller;

	private static final Pattern FILE_REGEX = Pattern.compile(".+\\.(html|css|js|ico|ttf|woff)");

	RequestMappingHandler(String path, Controller controller) {
		this.path = path;
		this.controller = controller;
	}

	public static Controller findController(HttpRequest httpRequest) {
		String path = httpRequest.getUrl().getPath();
		if (FILE_REGEX.matcher(path).matches()) {
			return FileController.getInstance();
		}
		return Arrays.stream(RequestMappingHandler.values())
			.filter(p -> path.startsWith(p.path))
			.findFirst()
			.orElseThrow(() -> new BadRequestException(path + ": 존재하는 경로가 없습니다.")).controller;

	}

}

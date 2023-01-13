package controller;

import java.util.Arrays;
import java.util.regex.Pattern;

import request.HttpRequest;

public enum RequestMappingHandler {

	USER_CONTROLLER("/create", new UserController());
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
			return new FileController();
		}
		return Arrays.stream(RequestMappingHandler.values())
			.filter(p -> path.startsWith(p.path))
			.findFirst()
			.orElseThrow(() -> new RuntimeException(path + ": 존재하는 경로가 없습니다.")).controller;

	}

}

package webserver;

import controller.Controller;
import controller.UserController;

public class RequestMappingHandler {

	public static Controller findController(HttpRequest httpRequest) {
		return new UserController(); // 여기서 request와 매치되는 controller 리턴해줘야함
	}
}

package controller;

import java.io.IOException;

import request.HttpRequest;
import response.HttpResponse;

public class FrontController implements Controller {

	private static FrontController instance;

	public static FrontController getInstance() {
		if (instance == null) {
			synchronized (FrontController.class) {
				instance = new FrontController();
			}
		}
		return instance;
	}
	@Override
	public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
		Controller controller = RequestMappingHandler.findController(httpRequest);

		controller.service(httpRequest, httpResponse);
	}
}

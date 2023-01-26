package view;

import java.io.IOException;

import model.Board;
import request.HttpRequest;
import response.HttpResponse;

public interface View {

	default void makeView(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException {
		// TODO 공통처리해주기 (로그인 표시 등)

		render(httpRequest, httpResponse, model);
	}

	void render(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException;
}

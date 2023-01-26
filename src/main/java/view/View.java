package view;

import java.io.IOException;

import model.Board;
import request.HttpRequest;
import response.HttpResponse;

public interface View {

	default void makeView(HttpRequest httpRequest, HttpResponse httpResponse, Model model) throws IOException {
		// TODO 공통처리해주기 (로그인 표시 등)
		String menubar;
		if (httpRequest.validSession()) {
			menubar = "<li><a href=\"#\" role=\"button\">로그아웃</a></li>\n"
				+ "                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>";
		} else {
			menubar = "<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>\n"
				+ "                <li><a href=\"user/form.html\" role=\"button\">회원가입</a></li>";
		}
		render(httpRequest, httpResponse, model, menubar);
	}

	void render(HttpRequest httpRequest, HttpResponse httpResponse, Model model, String menubar) throws IOException;
}

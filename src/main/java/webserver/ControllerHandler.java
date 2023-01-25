package webserver;

import controller.Controller;
import controller.HtmlController;
import controller.StaticFileController;
import controller.UserController;
import http.request.HttpRequest;

public class ControllerHandler {
    public static Controller handleController(HttpRequest httpRequest) {
        //TODO httpRequest 보고 어떤 컨트롤러 써야되는지 리턴 해줘야됨

        // POST 요청일 때 : 회원가입, 로그인
        // User의 요청이면 UserController 리턴
        if (httpRequest.isPost()) return whoWant(httpRequest.getUri());

        // html 파일을 원한다면?
        if (httpRequest.wantHtml()) return new HtmlController();

        // 나머지 - 정적 처리
        return new StaticFileController();

    }

    // ControllerHandler Util을 빼줘야 하나?

    public static Controller whoWant(String uri) {
        // User가 원하면 UserController를 넘겨줌
        if (isUser(uri)) return new UserController();

        //TODO 확장 : User 외에 다른 객체가 원한다면?
        return null;
    }

    public static boolean isUser(String uri) {
        return uri.startsWith("/user");
    }

}

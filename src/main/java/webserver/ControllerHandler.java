package webserver;

import controller.Controller;
import controller.StaticFileController;
import controller.UserController;
import http.request.HttpRequest;

public class ControllerHandler {
    public static Controller handleController(HttpRequest httpRequest) {
        //TODO httpRequest 보고 어떤 컨트롤러 써야되는지 리턴 해줘야됨

        // 어떤 기능 (회원가입, 로그인 등) 을 원한다면?
        if (httpRequest.wantDynamic()) {
            // 누가 원하는지 > 누구의 컨트롤러가 필요한지 넘겨줌
            // User가 원하면 UserController
            return whoWant(httpRequest.getUri());
        }

        // 정적 파일만 원한다면? ex) index.html, /user/form.html
        if (httpRequest.wantStatic()) return new StaticFileController();


        //TODO 아무것도 없으면 나중에 예외처리
        return null;
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

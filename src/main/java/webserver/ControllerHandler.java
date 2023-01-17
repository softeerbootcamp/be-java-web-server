package webserver;

import controller.Controller;
import controller.DynamicFileController;
import controller.StaticFileController;
import controller.UserController;
import http.request.HttpRequest;

import javax.annotation.Nonnull;

public class ControllerHandler {
    public static Controller handleController(HttpRequest httpRequest) {
        //TODO httpRequest 보고 어떤 컨트롤러 써야되는지 리턴 해줘야됨

        // 어떤 기능 (회원가입, 로그인 등) 을 원한다면?
        // POST 요청일 때
        if (httpRequest.isPost()) {
            // 누가 원하는지 > 누구의 컨트롤러가 필요한지 넘겨줌
            // User가 원하면 UserController
            return whoWant(httpRequest.getUri());
        }

        // 파일을 원한다면? ex) index.html, /user/form.html, .css, .js

        // .html 파일을 원한다면 - 동적 처리
        if(httpRequest.wantHtml()){
            String userId = httpRequest.checkLoginStatus();
            // 쿠키 체크 해서 쿠키에 맞는 user 있는지 확인
            if(userId != null){
                // 로그인 상태인 경우에 user 넘겨줘서 처리
                return new DynamicFileController();
            }
        }

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

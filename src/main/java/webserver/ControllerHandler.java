package webserver;

import Controller.Controller;
import Controller.UserController;
import Controller.StaticFileController;
import http.HttpRequest;

public class ControllerHandler {
    public static Controller handleController(HttpRequest httpRequest){
        //TODO httpRequest 보고 어떤 컨트롤러 써야되는지 리턴 해줘야됨

        // 정적 파일만 원한다면? ex) index.html, /user/form.html
        if(httpRequest.wantStaticFile()) return new StaticFileController();

        // user 가 어떤 기능 (회원가입, 로그인 등) 을 원한다면?
        if(httpRequest.getUri().startsWith("/user")) return new UserController();


        //TODO 아무것도 없으면 나중에 예외처리
        return null;
    }

}

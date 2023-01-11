package webserver;

import Controller.Controller;
import Controller.UserController;
import Controller.StaticFileController;
import http.HttpRequest;

public class ControllerHandler {
    public static Controller handleController(HttpRequest httpRequest){
        //TODO httpRequest 보고 어떤 컨트롤러 써야되는지 리턴 해줘야됨

        if(httpRequest.getUri().startsWith("/user")) return new UserController();
        return new StaticFileController();
    }

}

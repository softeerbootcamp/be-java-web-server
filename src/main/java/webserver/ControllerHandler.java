package webserver;

import Controller.Controller;
import Controller.UserController;
import http.HttpRequest;

public class ControllerHandler {
    public static Controller handleController(HttpRequest httpRequest){
        //TODO httpRequest 보고 어떤 컨트롤러 써야되는지 리턴 해줘야됨 일단은 UserController 리턴
        return new UserController();
    }

}

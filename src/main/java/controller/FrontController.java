package controller;

import java.util.HashMap;
import java.util.Map;

public class FrontController {
    private static final Map<String,Controller> controllers;
    private static final String SIGN_UP_PATH_URL = "/user/create";

    //객체 캐싱
    static{
        controllers = new HashMap<>();
        SignUpController signUpController = new SignUpController();
        controllers.put(SIGN_UP_PATH_URL,signUpController);
    }

    public Controller getControllerByUrl(String pathUrl){
        return controllers.get(pathUrl);
    }

}

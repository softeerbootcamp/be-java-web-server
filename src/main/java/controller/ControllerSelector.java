package controller;

import request.Request;
import request.RequestLine;

public class ControllerSelector {
    private static final TemplateController templateController = new TemplateController();
    private static final UserController userController = new UserController();
    private RequestLine requestLine;
    private static final int _TOKEN_INDEX = 1;

    public static Controller setController(RequestLine requestLine){
        if(requestLine.getURL().split("\\.")[_TOKEN_INDEX].equals("html")){
            return templateController;
        }
        if (requestLine.getUserRequest()){
            return userController;
        }
        return null;
    }
}

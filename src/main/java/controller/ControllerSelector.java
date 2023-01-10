package controller;

import request.Request;
import request.RequestLine;

public class ControllerSelector {
    private static final TemplateController templateController = new TemplateController();
    private RequestLine requestLine;
    private static final int _TOKEN_INDEX = 1;

    public Controller setController(RequestLine requestLine){
        if(requestLine.getURL().split(".")[_TOKEN_INDEX]=="html"){
            return templateController;
        }
        return null;
    }
}

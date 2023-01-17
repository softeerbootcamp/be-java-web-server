package controller;

import enums.ControllerTypeEnum;
import enums.MethodEnums;
import request.Request;
import request.RequestLine;

public class ControllerSelector {
    private static final TemplateController templateController = new TemplateController();
    private static final UserController userController = new UserController();

    private static final StaticController staticController = new StaticController();
    private RequestLine requestLine;
    private static final int _TOKEN_INDEX = 1;

    public static Controller setController(Request request){
        String method = request.getRequestLine().getMETHOD();
        RequestLine requestLine = request.getRequestLine();
        // todo : 나!!누!!자!!
        if(method.equals(MethodEnums.GET.getValue())){
            if(requestLine.getURL().split("\\.")[_TOKEN_INDEX].equals("html")||
                    requestLine.getURL().split("\\.")[_TOKEN_INDEX].equals("ico")){
                return templateController;
            }
            return staticController;
        }
        if(method.equals(MethodEnums.POST.getValue())){
            return userController;
        }
        return staticController;
    }
}

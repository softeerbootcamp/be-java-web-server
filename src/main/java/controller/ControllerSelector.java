package controller;

import enums.ControllerTypeEnum;
import enums.MethodEnums;
import request.Request;
import request.RequestLine;

public class ControllerSelector {
    private static final TemplateController templateController = new TemplateController();
    private static final UserController userController = new UserController();

    private static final StaticController staticController = new StaticController();
    private static final LoginController loginController = new LoginController();
    private static final int _TOKEN_INDEX = 1;

    public static Controller setController(Request request) {
        String method = request.getRequestLine().getMETHOD();
        String url = request.getRequestLine().getURL();
        // todo : 나!!누!!자!!
        if (method.equals(MethodEnums.GET.getValue())) {
            if (request.getRequestLine().getURL().split("\\.")[_TOKEN_INDEX].equals("html") ||
                    request.getRequestLine().getURL().split("\\.")[_TOKEN_INDEX].equals("ico")) {
                return templateController;
            }
            return staticController;
        }
        // todo : 여기도 사실 url 만 있으면 되는게 아닐까? post 확인해야될까?
        if (method.equals(MethodEnums.POST.getValue())) {
            if (url.contains("/login")) {
                return loginController;
            }
            if(url.contains("/create")){

                return userController;
            }
        }
        return staticController;
    }
}

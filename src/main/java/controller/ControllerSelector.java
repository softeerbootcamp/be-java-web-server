package controller;

import enums.ControllerTypeEnum;
import enums.MethodEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Request;
import request.RequestLine;
import webserver.RequestResponseHandler;

public class ControllerSelector {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseHandler.class);
    private static final TemplateController templateController = new TemplateController();
    private static final UserController userController = new UserController();
    private static final StaticController staticController = new StaticController();
    private static final LoginController loginController = new LoginController();
    private static final int _TOKEN_INDEX = 1;

    public static Controller setController(Request request) {
        String method = request.getRequestLine().getMETHOD();
        String url = request.getRequestLine().getURL();
        if (method.equals(MethodEnums.GET.getValue())) {
            String template = url.split("\\.")[_TOKEN_INDEX];
            if (template.equals("html") || template.equals("ico")) {
                return templateController;
            }
        }
        if (method.equals(MethodEnums.POST.getValue())) {
            if (url.contains("/login")) {
                return loginController;
            }
            if (url.contains("/create")) {
                return userController;
            }
        }
        return staticController;
    }
}

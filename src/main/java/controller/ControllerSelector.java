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
        if (url.split("\\.")[_TOKEN_INDEX].equals("html") ||
                url.split("\\.")[_TOKEN_INDEX].equals("ico")) {
            return templateController;
        }
        /* todo : 여기도 사실 url 만 있으면 되는게 아닐까?
            post 확인해야될까? 3주차 까지 진행 해 보았는데, 아직 post 확인의 필요성을 못느끼고 있다.
            추후 과정에서 쓸 일이 있으면 추가하자.
        */
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

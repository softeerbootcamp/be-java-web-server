package controller;

import request.HttpRequest;

import java.util.Map;
import java.util.Objects;

public class ControllerHandler {
    private final static Map<String, Controller> cons;

    static {
        cons = Map.of(JoinController.PATH, JoinController.getInstance(),
                LoginController.PATH, LoginController.getInstance(),
                UserListController.PATH, UserListController.getInstance(),
                FileController.PATH, FileController.getInstance(),
                IndexController.PATH, IndexController.getInstance(),
                QnaCreateController.PATH, QnaCreateController.getInstance(),
                QnaShowController.PATH, QnaShowController.getInstance());
    }

    public static Controller match(HttpRequest httpRequest) {
        String path = httpRequest.getUrl();

        Controller controller = cons.get(path);
        if (Objects.isNull(controller)) {
            return NonController.getInstance();
        }
        return controller;
    }
}

package controller;

import request.HttpRequest;

import java.util.Map;
import java.util.Objects;

public class MatchController {
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

    public static Controller matching(HttpRequest httpRequest) {
        String path = httpRequest.getPath();

        Controller controller = cons.get(filePath(path));
        if (Objects.isNull(controller)) {
            return NonController.getInstance();
        }
        return controller;
    }
    private static String filePath(String path) {
        if (path.contains(".")) {
            return "";
        }
        if (path.startsWith("/qna/show")){
            return "/qna/show";
        }
        return path;
    }
}

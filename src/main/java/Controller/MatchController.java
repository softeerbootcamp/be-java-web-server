package Controller;

import Request.HttpRequest;
import db.SessionDb;
import model.User;
import util.FileIoUtil;
import util.LoginUtil;

import java.io.DataOutputStream;
import java.nio.file.Path;
import java.util.Objects;

public class MatchController {
    public static Controller match(HttpRequest httpRequest) {
        String requestPath = httpRequest.getPath();
        Path path = FileIoUtil.mappingDirectoryPath(requestPath);
        if (requestPath.startsWith("/user/list")) {
            return new UserListController();
        }
        if (requestPath.startsWith("/user/login") && !requestPath.contains(".")) {
            return new LoginController();
        }
        if (requestPath.startsWith("/user/create")) {
            return new JoinController();
        }
        try {
            if (path.toFile().exists()) {
                return new FileController();
            }
        } catch (NullPointerException e) {
            return new NonController();
        }
        return new NonController();
    }

}

package Controller;

import Request.HttpRequest;
import util.FileIoUtil;

import java.io.DataOutputStream;
import java.nio.file.Path;
import java.util.Objects;

public class MatchController {
    public static Controller match(DataOutputStream dos, HttpRequest httpRequest) {
        String requestPath = httpRequest.getPath();
        Path path = FileIoUtil.mappingDirectoryPath(requestPath);
        if (requestPath.startsWith("/user/login") && !requestPath.contains(".")) {
            return new LoginController(httpRequest, dos);
        }
        if (requestPath.startsWith("/user/create")) {
            return new JoinController(httpRequest, dos);
        }
        try {
            if (path.toFile().exists()) {
                return new FileController(httpRequest, dos);
            }
        } catch (NullPointerException e) {
            return new NonController(httpRequest, dos);
        }
        return new NonController(httpRequest, dos);
    }
}

package Controller;

import Request.HttpRequest;
import util.FileIoUtil;

import java.io.DataOutputStream;
import java.nio.file.Path;
import java.util.Objects;

public class MappingController {
    public static Controller mapController(DataOutputStream dos, HttpRequest httpRequest) {
        String requestPath = httpRequest.getPath();
        Path path = FileIoUtil.mappingDirectoryPath(requestPath);

        if (Objects.nonNull(FileIoUtil.checkCreateUser(requestPath))) {
            return new JoinController(httpRequest, dos);
        }
        if (path.toFile().exists()) {
            return new FileController(httpRequest, dos);
        }
        return new NonController(httpRequest, dos);
    }
}

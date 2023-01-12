package Controller;

import Request.HttpRequest;
import util.FileIoUtil;

import java.io.DataOutputStream;
import java.nio.file.Path;
import java.util.Objects;

public class MappingController {
    public static Controller mapController(DataOutputStream dos, HttpRequest httpRequest){
        String requestPath = httpRequest.getPath();
        Path path = FileIoUtil.mappingPath(requestPath);
        if(Objects.isNull(requestPath) || Objects.isNull(path)){
            return new NonController(httpRequest, dos);
        }
        if(Objects.isNull(FileIoUtil.checkCreateUser(requestPath))){
            return new FileController(httpRequest, dos);
        }
        return new JoinController(httpRequest, dos);
    }
}

package Controller;

import Request.HttpRequest;
import util.FileIoUtil;

import java.io.DataOutputStream;
import java.util.Objects;

public class MappingController {
    public static Controller mapController(DataOutputStream dos, HttpRequest httpRequest){
        String path = httpRequest.getPath();
        if(Objects.isNull(path)){
            return new NonController(httpRequest, dos);
        }
        if(Objects.isNull(FileIoUtil.checkCreateUser(path))){
            return new FileController(httpRequest, dos);
        }
        return new JoinController(httpRequest, dos);
    }
}

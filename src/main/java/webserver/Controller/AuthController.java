package webserver.Controller;

import webserver.Service.AuthService;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.CommonUtils;
import webserver.utils.HttpRequestUtils;
import webserver.utils.HttpResponseUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static webserver.utils.CommonUtils.checkParameters;

public class AuthController implements Controller {

    private Response res;
    private AuthService authService;

    public AuthController(){
        this.authService = new AuthService();
    }

    private void userCreate(Map<String, String> queryStrs) {
        if(!checkParameters(List.of("userId", "password", "name", "email"), queryStrs)) {
            throw new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR);
        }
        byte[] result = authService.join(queryStrs.get("userId"), queryStrs.get("password"), queryStrs.get("name"), queryStrs.get("email"));
        res.setStatusCode(StatusCodes.SEE_OTHER);
        res.setBody(result);
        res.setContentType(ContentType.TEXT_HTML);
        res.setRedirection("http://localhost:8080/index.html");
    }


    @Override
    public void handle(String path, String queryString, Response res) throws HttpRequestException {
        this.res = res;
        switch (Enum.find(path)){
            case CREATE:
                Map<String, String> queryStrs = CommonUtils.parseQueryStrings(queryString);
                userCreate(queryStrs);
        }
    }

    public enum Enum{
        CREATE("/user/create");

        private String path;

        private Enum(String path){
            this.path = path;
        }
        public static Enum find(String req){
            return Arrays.stream(Enum.values())
                    .filter(each-> req.startsWith(each.getPath()))
                    .findFirst()
                    .orElseThrow(() -> new HttpRequestException(StatusCodes.NOT_FOUND));
        }
        public String getPath(){
            return path;
        }
    }
}

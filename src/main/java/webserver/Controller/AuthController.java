package webserver.Controller;

import webserver.ArgumentResolver;
import webserver.Service.AuthService;
import webserver.domain.ContentType;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static webserver.ArgumentResolver.checkParameters;

public class AuthController implements Controller {

    private Response response;
    private AuthService authService;

    public AuthController(){
        this.authService = new AuthService();
    }

    public void userCreate(Map<String, String> queryStrs) throws HttpRequestException{
        byte[] result = authService.join(queryStrs.get("userId"), queryStrs.get("password"), queryStrs.get("name"), queryStrs.get("email"));
        response.redirect(StatusCodes.SEE_OTHER, result, ContentType.TEXT_HTML, "http://localhost:8080/index.html");
    }

    @Override
    public void chain(String path, Map<String, String> queryString, Response res) throws HttpRequestException{
        response = res;
        try{
            switch (RouterPath.find(path)){
                case CREATE:
                    checkParameters(queryString, List.of("userId", "password", "name", "email"));
                    userCreate(queryString);
            }
        }catch (HttpRequestException e){
            response.error(e.getErrorCode());
        }
    }

    public enum RouterPath{
        CREATE("/user/create");

        private String path;

        private RouterPath(String path){
            this.path = path;
        }
        public static RouterPath find(String req){
            return Arrays.stream(RouterPath.values())
                    .filter(each-> req.startsWith(each.getPath()))
                    .findFirst()
                    .orElseThrow(() -> new HttpRequestException(StatusCodes.NOT_FOUND));
        }
        public String getPath(){
            return path;
        }
    }
}

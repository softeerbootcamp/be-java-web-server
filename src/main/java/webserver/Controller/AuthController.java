package webserver.Controller;

import webserver.Service.AuthService;
import webserver.domain.StatusCodes;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import webserver.utils.CommonUtils;
import webserver.utils.HttpRequestUtils;
import webserver.utils.HttpResponseUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

public class AuthController implements Controller {

    private Response res;
    private AuthService authService;

    public AuthController(){
        this.authService = new AuthService();
    }

    public void userCreate(String userId, String password, String name, String email) throws HttpRequestException{
        if(userId == null || userId == null || userId == null || name == null){
            throw new HttpRequestException(StatusCodes.INTERNAL_SERVER_ERROR.getStatusCode(), StatusCodes.INTERNAL_SERVER_ERROR.getStatusMsg());
        }
        byte[] result = authService.join(userId, password, name, email);
        res.makeResponse(result, StatusCodes.OK.getStatusCode(), StatusCodes.OK.getStatusMsg());
    }

    @Override
    public void handle(String request, Response res) throws HttpRequestException {
        this.res = res;
        String [] parsedReq = request.split("\\?");
        String path = parsedReq[0];
        String queryString = parsedReq[1];
        switch (Enum.find(path)){
            case CREATE:
                Map<String, String> queryStrs = CommonUtils.parseQueryStrings(queryString);
                String userId = queryStrs.get("userId");
                String password = queryStrs.get("password");
                String name = queryStrs.get("name");
                String email = queryStrs.get("email");
                userCreate(userId, password, name, email);
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
                    .orElseThrow(() -> new HttpRequestException("404", "NOT FOUND"));
        }
        public String getPath(){
            return path;
        }
    }
}

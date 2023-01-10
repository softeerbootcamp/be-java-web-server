package webserver.Controller;

import webserver.Service.AuthService;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;
import webserver.utils.CommonUtils;
import webserver.utils.HttpResponseUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

public class AuthController implements Controller {

    private OutputStream out;
    private AuthService authService;

    public AuthController(){
        this.authService = new AuthService();
    }

    public void userJoin(String userId, String password, String name, String email) throws HttpRequestException, IOException {
        if(userId == null || userId == null || userId == null || name == null){
            throw new HttpRequestException("500", "INTERNAL_SERVER_ERROR");
        }
        byte[] result = authService.join(userId, password, name, email);
        StatusCodes code = StatusCodes.findStatus("OK");
        HttpResponseUtil.makeResponse(result, out, code.getStatusCode(), code.getStatusMsg());
    }

    @Override
    public void handle(String request, OutputStream out) throws HttpRequestException, IOException {
        this.out = out;
        switch (Enum.find(request)){
            case JOIN:
                Map<String, String> queryStrs = CommonUtils.parseQueryStrings(request);
                String userId = queryStrs.get("userId");
                String password = queryStrs.get("password");
                String name = queryStrs.get("name");
                String email = queryStrs.get("email");
                userJoin(userId, password, name, email);
        }
    }


    public enum Enum{
        JOIN("/user/join");

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

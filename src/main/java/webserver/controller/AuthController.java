package webserver.controller;

import webserver.ControllerInterceptor;
import webserver.Service.AuthService;
import webserver.annotation.ControllerInfo;
import webserver.domain.ContentType;
import webserver.domain.RequestMethod;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.io.IOException;
import java.util.Map;

public class AuthController implements Controller {

    private AuthService authService;
    
    public AuthController(){
        this.authService = new AuthService();
    }


    @ControllerInfo(path = "/user/create", methodName = "userCreate", queryStr = {"userId", "password", "name", "email"}, method = RequestMethod.POST)
    public void userCreate(Map<String, String> queryStrs, Response response) throws HttpRequestException{
        //TODO: authService.join의 리턴값 핸들링, javascript 문법 하드 코딩 개선
        byte[] result = authService.join(queryStrs.get("userId"), queryStrs.get("password"), queryStrs.get("name"), queryStrs.get("email"));
        response.ok(
                StatusCodes.OK,
                ("<script>alert('계정 생성이 완료되었습니다.'); window.location.href = 'http://localhost:8080/index.html';</script>").getBytes(),
                ContentType.TEXT_HTML
            );
    }

    @Override
    public void chain(Request req, Response res) throws HttpRequestException, IOException {
        try{
            ControllerInterceptor.executeController(AuthController.class, req, res);
        }catch (HttpRequestException e){
            res.error(e.getErrorCode(), e.getMsg().getBytes(), ContentType.TEXT_HTML);
        }
    }
}

package Controller;

import http.HttpHeader;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpStatus;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import webserver.RequestHandler;

import java.io.IOException;
import java.util.Map;

public class UserController implements Controller{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Override
    public HttpResponse makeResponse(HttpRequest httpRequest) throws IOException {
        // Uri  받아옵시다
        String uri = httpRequest.getUri();

        // 회원가입일 때
        // TODO 할 일 enum 으로 구현 가능 할듯

        // TODO 회원가입에 관한 처리 REFACTORING 예정
        if (uri.startsWith("/user/create")) {
            // TODO split으로 변경 후 메소드 분리
            int index = uri.indexOf("?");
            String requestPath = uri.substring(0, index);
            String queryString = uri.substring(index + 1);
            logger.debug("QueryString : {}", queryString);
            Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
            User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
            logger.debug("User : {}", user);

            //TODO 임시 코드 - 아마 status 300번대?
            byte[] responseBody = HttpResponse.makeBody(httpRequest);
            return new HttpResponse(HttpStatus.OK, responseBody);
        }

        //TODO 임시 코드 - /user/form.html 같이 요청 하면 이거 줘야됨
        byte[] responseBody = HttpResponse.makeBody(httpRequest);
        return new HttpResponse(HttpStatus.OK, responseBody);

    }
}

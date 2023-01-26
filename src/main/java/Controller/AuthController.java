package Controller;

import Request.HttpRequest;
import Request.StatusCode;
import response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtil;
import util.LoginUtil;

import java.io.DataOutputStream;

public interface AuthController extends Controller {
    Logger logger = LoggerFactory.getLogger(AuthController.class);
    String USER_LOGIN_HTML = "/user/login.html";

    @Override
    default void response(HttpRequest httpRequest, DataOutputStream dos) {
        try {
            LoginUtil.checkSession(httpRequest);
        } catch (NullPointerException e) {
            logger.debug("user login");
            HttpResponse httpResponse = HttpResponse.createResponse(USER_LOGIN_HTML, StatusCode.FORBIDDEN, httpRequest.getProtocol());
            HttpResponseUtil.sendResponse(dos, httpResponse);
            return;
        }
        Controller.super.response(httpRequest, dos);
    }


}

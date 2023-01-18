package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtil;
import util.LoginUtil;

import java.io.DataOutputStream;

public interface AuthController extends Controller {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Override
    default void response(HttpRequest httpRequest, DataOutputStream dos) {
        try {
            LoginUtil.checkSession(httpRequest);
        } catch (NullPointerException e) {
            logger.debug("user login redirect");
            HttpResponse httpResponse = redirect("/user/login.html", httpRequest);
            HttpResponseUtil.sendResponse(dos, httpResponse);
            return;
        }
        Controller.super.response(httpRequest, dos);
    }


}

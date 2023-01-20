package Controller;

import Request.HttpRequest;
import Request.StatusCode;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
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
            byte[] body = HttpResponseUtil.generateBody("/user/login.html");
            logger.debug("user login");
            HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.FORBIDDEN, httpRequest.getProtocol()))
                                            .headers(new HttpResponseHeaders("/user/login.html", body.length))
                                            .body(new HttpResponseBody(body));
            HttpResponseUtil.sendResponse(dos, httpResponse);
            return;
        }
        Controller.super.response(httpRequest, dos);
    }


}

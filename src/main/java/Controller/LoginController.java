package Controller;

import Request.HttpRequest;
import Request.StatusCode;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import db.SessionDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtil;
import util.LoginUtil;

public class LoginController implements Controller {
    public static final String INDEX_HTML = "/index.html";
    public static final String USER_LOGIN_FAILED_HTML = "/user/login_failed.html";
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController() {
        logger.debug("select LoginController");
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        String sessionId;
        if (LoginUtil.checkUserInfoMatch(httpRequest)) {
            sessionId = SessionDb.saveNewSession(httpRequest);
            return responseRedirectIndex(httpRequest, sessionId);
        }
        return responseLoginFailed(httpRequest);
    }

    private HttpResponse responseRedirectIndex(HttpRequest httpRequest, String sessionId) {
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.FOUND, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders("", 0))
                .body(new HttpResponseBody("".getBytes()));
        response.putHeader("Location", INDEX_HTML);
        response.putHeader("Set-Cookie", "sid=" + sessionId + "; Path=/");

        return response;
    }

    private HttpResponse responseLoginFailed(HttpRequest httpRequest) {
        byte[] body = HttpResponseUtil.generateBody(USER_LOGIN_FAILED_HTML);
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders(USER_LOGIN_FAILED_HTML, body.length))
                .body(new HttpResponseBody(body));
        return response;
    }

}

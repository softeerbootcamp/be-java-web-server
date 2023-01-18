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

import java.io.DataOutputStream;

public class LoginController implements Controller {
    public static final String INDEX_HTML = "/index.html";
    public static final String USER_LOGIN_FAILED_HTML = "/user/login_failed.html";
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    HttpRequest httpRequest;

    public LoginController(HttpRequest httpRequest) {
        logger.debug("select LoginController");
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponse createResponse() {
        String sessionId;
        if (LoginUtil.checkUserInfoMatch(httpRequest)) {
            sessionId = SessionDb.saveNewSession(httpRequest);
            return responseRedirectIndex(sessionId);
        }
        return responseLoginFailed();
    }

    private HttpResponse responseRedirectIndex(String sessionId) {
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.FOUND, this.httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders("", 0))
                .body(new HttpResponseBody("".getBytes()));
        response.putHeader("Location", INDEX_HTML);
        response.putHeader("Set-Cookie", "sid=" + sessionId + "; Path=/");

        return response;
    }

    private HttpResponse responseLoginFailed() {
        byte[] body = HttpResponseUtil.generateBody(USER_LOGIN_FAILED_HTML);
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, this.httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders(USER_LOGIN_FAILED_HTML, body.length))
                .body(new HttpResponseBody(body));
        return response;
    }

}

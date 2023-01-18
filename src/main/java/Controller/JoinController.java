package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;
import util.HttpResponseUtil;
import util.ManageDB;
import Request.StatusCode;

import java.io.DataOutputStream;
public class JoinController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(JoinController.class);
    HttpRequest httpRequest;
    public JoinController(HttpRequest httpRequest) {
        logger.debug("select joinController");
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponse createResponse() {
        if (ManageDB.alreadyExist(this.httpRequest)) {
            return responseUserForm();
        }
        ManageDB.saveUser(this.httpRequest);
        return responseRedirectIndex();
    }

    private HttpResponse responseRedirectIndex() {
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.FOUND, this.httpRequest.getProtocol()))
                                    .headers(new HttpResponseHeaders("", 0))
                                    .body(new HttpResponseBody("".getBytes()));
        response.putHeader("Location", "/index.html");
        return response;
    }

    private HttpResponse responseUserForm() {
        byte[] body = HttpResponseUtil.generateBody("/user/form.html");
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, this.httpRequest.getProtocol()))
                                    .headers(new HttpResponseHeaders("/user/form.html", body.length))
                                    .body(new HttpResponseBody(body));
        return response;
    }

}

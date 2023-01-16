package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import Response.HttpResponseBody;
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
    DataOutputStream dos;
    public JoinController(HttpRequest httpRequest, DataOutputStream dos) {
        logger.debug("select joinController");
        this.httpRequest  = httpRequest;
        this.dos = dos;
    }

    @Override
    public HttpResponse createResponse() {
        if(ManageDB.alreadyExist(this.httpRequest)) {
            return responseUserForm();
        }
        ManageDB.saveUser(this.httpRequest);
        return responseRedirectIndex();
    }
    private HttpResponse responseRedirectIndex(){
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.FOUND, this.httpRequest.getProtocol()))
                .headers(HttpResponseUtil.generateHeaders("", StatusCode.FOUND, 0))
                .body(new HttpResponseBody("".getBytes()));
        response.putHeader("Location", "/index.html");
        return response;
    }
    private HttpResponse responseUserForm(){
        byte[] body  = HttpResponseUtil.generateBody("/user/form.html");
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, this.httpRequest.getProtocol()))
                .headers(HttpResponseUtil.generateHeaders("/user/form.html", StatusCode.OK, body.length))
                .body(new HttpResponseBody(body));
        return response;
    }

}

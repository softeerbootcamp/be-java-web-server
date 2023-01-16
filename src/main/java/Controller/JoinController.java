package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import Response.HttpResponseStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        ManageDB.saveUser(this.httpRequest);

        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.FOUND, httpRequest.getProtocol()))
                .headers(HttpResponseUtil.generateHeaders("", StatusCode.NOT_FOUND, 0));
        response.putHeader("Location", "/index.html");
        return response;
    }
}

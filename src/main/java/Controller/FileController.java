package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Request.StatusCode;
import util.HttpResponseUtil;

import java.io.DataOutputStream;

public class FileController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    HttpRequest httpRequest;
    DataOutputStream dos;
    public FileController(HttpRequest httpRequest, DataOutputStream dos) {
        logger.debug("select fileController");
        this.httpRequest = httpRequest;
        this.dos = dos;
    }

    @Override
    public HttpResponse createResponse() {
        byte[] body = HttpResponseUtil.generateBody(httpRequest.getPath());
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(HttpResponseUtil.generateHeaders(httpRequest.getPath(), StatusCode.OK, body.length))
                .body(new HttpResponseBody(body));
        return  httpResponse;
    }
}

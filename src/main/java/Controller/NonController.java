package Controller;

import Request.HttpRequest;
import Response.ContentType;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Request.StatusCode;
import util.HttpResponseUtil;

import java.io.DataOutputStream;
import java.util.Map;

public class NonController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(NonController.class);
    HttpRequest httpRequest;
    DataOutputStream dos;

    public NonController(HttpRequest httpRequest, DataOutputStream dos) {
        logger.debug("select NonController");
        this.httpRequest = httpRequest;
        this.dos = dos;
    }

    @Override
    public HttpResponse createResponse() {
        if (this.httpRequest.getPath().equals("/")) {
            return responseRoot();
        }
        return response404();
    }

    public HttpResponse response404() {
        logger.debug("[response404]");
        byte[] body = "404: 존재하지않는 파일입니다.".getBytes();
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.NOT_FOUND, httpRequest.getProtocol()))
                .headers(HttpResponseUtil.generateHeaders("", StatusCode.NOT_FOUND, body.length))
                .body(new HttpResponseBody(body));
        return httpResponse;
    }

    public HttpResponse responseRoot() {
        logger.debug("[responseRoot]");
        byte[] body = HttpResponseUtil.generateBody("/index.html");
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(HttpResponseUtil.generateHeaders("/index.html", StatusCode.OK, body.length))
                .body(new HttpResponseBody(body));

        return httpResponse;
    }
}

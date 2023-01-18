package Controller;

import Request.HttpRequest;
import Response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Request.StatusCode;
import util.HttpResponseUtil;

import java.io.DataOutputStream;

public class NonController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(NonController.class);
    public static final String INDEX_HTML = "/index.html";

    HttpRequest httpRequest;
    public NonController(HttpRequest httpRequest) {
        logger.debug("select NonController");
        this.httpRequest = httpRequest;
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
                .headers(new HttpResponseHeaders("", body.length))
                .body(new HttpResponseBody(body));
        return httpResponse;
    }

    public HttpResponse responseRoot() {
        logger.debug("[responseRoot]");
        byte[] body = HttpResponseUtil.generateBody(INDEX_HTML);
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders(INDEX_HTML, body.length))
                .body(new HttpResponseBody(body));

        return httpResponse;
    }
}

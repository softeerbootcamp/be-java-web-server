package Controller;

import Request.HttpRequest;
import Response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Request.StatusCode;
import util.HttpResponseUtil;


public class NonController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(NonController.class);
    public static final String INDEX_HTML = "/index.html";


    public NonController() {
        logger.debug("select NonController");
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        if (httpRequest.getPath().equals("/")) {
            return responseRoot(httpRequest);
        }
        return response404(httpRequest);
    }

    public HttpResponse response404(HttpRequest httpRequest) {
        logger.debug("[response404]");
        byte[] body = "404: 존재하지않는 파일입니다.".getBytes();
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.NOT_FOUND, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders("", body.length))
                .body(new HttpResponseBody(body));
        return httpResponse;
    }

    public HttpResponse responseRoot(HttpRequest httpRequest) {
        logger.debug("[responseRoot]");
        byte[] body = HttpResponseUtil.generateBody(INDEX_HTML);
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders(INDEX_HTML, body.length))
                .body(new HttpResponseBody(body));

        return httpResponse;
    }
}

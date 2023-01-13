package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtil;
import util.StatusCode;

import java.io.DataOutputStream;

public class NonController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(NonController.class);

    public NonController(HttpRequest httpRequest, DataOutputStream dos) {
        super(httpRequest, dos);
        logger.debug("select nonController");
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
        HttpResponse httpResponse = HttpResponse.createHttpResponse(httpRequest.getPath(), StatusCode.NOT_FOUND, httpRequest.getProtocol());
        byte[] body = "존재하지않는 파일입니다.".getBytes();
        httpResponse.setBody(body);
        httpResponse.putHeaders("Content-Length", String.valueOf(body.length));
        return httpResponse;
    }

    public HttpResponse responseRoot() {
        logger.debug("[responseRoot]");
        HttpResponse httpResponse1 = HttpResponse.createHttpResponse("/index.html", StatusCode.OK, httpRequest.getProtocol());
        return httpResponse1;
    }
}

package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Request.StatusCode;
import util.HttpResponseUtil;

public class FileController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController() {
        logger.debug("select fileController");
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        byte[] body = HttpResponseUtil.generateBody(httpRequest.getPath());
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders(httpRequest.getPath(), body.length))
                .body(new HttpResponseBody(body));
        return httpResponse;
    }
}

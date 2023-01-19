package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import Request.StatusCode;
import util.HtmlBuildUtil;
import util.HttpResponseUtil;
import util.LoginUtil;

public class FileController implements Controller {
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController() {
        logger.debug("select fileController");
    }

    @Override
    public HttpResponse createResponse(HttpRequest httpRequest) {
        byte[] body = createBody(httpRequest);
        HttpResponse httpResponse = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.OK, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders(httpRequest.getPath(), body.length))
                .body(new HttpResponseBody(body));
        return httpResponse;
    }
    private byte[] createBody(HttpRequest httpRequest) {
        try {
            User user = LoginUtil.checkSession(httpRequest);
            return HtmlBuildUtil.buildHtml(httpRequest.getPath(), user);
        } catch (NullPointerException e) {
            return HttpResponseUtil.generateBody(httpRequest.getPath());
        }
    }
}

package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileIoUtil;
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
    public void response() {
        super.response();
        if (this.httpRequest.getPath().equals("/")) {
            this.httpResponse = responseRoot();
        }
        this.httpResponse = response404();
        HttpResponseUtil.outResponse(dos, httpResponse);
    }

    public HttpResponse response404() {
        HttpResponse httpResponse = HttpResponse.createHttpResponse(httpRequest, StatusCode.NOT_FOUND);
        return httpResponse;
    }

    public HttpResponse responseRoot() {
        HttpResponse httpResponse = HttpResponse.createHttpResponse(httpRequest, StatusCode.OK);
        httpResponse.setBody(FileIoUtil.mappingDirectoryPath("/index.html"));
        httpResponse.setHeaders(HttpResponseUtil.generateHeaders(httpRequest, StatusCode.OK, httpResponse.getBody().length));
        return httpResponse;
    }

}

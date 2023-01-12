package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtil;
import util.StatusCode;

import java.io.DataOutputStream;

public class FileController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(HttpRequest httpRequest, DataOutputStream dos) {
        super(httpRequest, dos);
        logger.debug("select fileController");
    }

    @Override
    public void response() {
        super.response();
        this.httpResponse = HttpResponse.createHttpResponse(this.httpRequest, StatusCode.OK);
        HttpResponseUtil.outResponse(this.dos, this.httpResponse);
    }
}

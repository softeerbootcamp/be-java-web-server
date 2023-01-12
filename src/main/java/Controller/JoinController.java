package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpResponseUtil;
import util.ManageDB;
import util.StatusCode;

import java.io.DataOutputStream;

public class JoinController extends Controller {
    private final Logger logger = LoggerFactory.getLogger(JoinController.class);

    public JoinController(HttpRequest httpRequest, DataOutputStream dos) {
        super(httpRequest, dos);
        logger.debug("select joinController");
    }

    @Override
    public void response() {
        super.response();
        ManageDB.saveUser(this.httpRequest);
        this.httpResponse = HttpResponse.createHttpResponse(this.httpRequest, StatusCode.FOUND);
        this.httpResponse.putHeaders("Location", "/index.html");
        this.httpResponse.putHeaders("Content-Length", "0");
        HttpResponseUtil.outResponse(this.dos, this.httpResponse);
    }
}

package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public HttpResponse createResponse() {
        ManageDB.saveUser(this.httpRequest);

        HttpResponse response = HttpResponse.createHttpResponse("", StatusCode.FOUND, httpRequest.getProtocol());
        response.putHeaders("Location", "/index.html");
        return response;
    }
}

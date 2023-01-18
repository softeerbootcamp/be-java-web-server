package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import util.HttpResponseUtil;

import java.io.DataOutputStream;

public interface Controller {

    HttpResponse createResponse(HttpRequest httpRequest);

    default void response(HttpRequest httpRequest, DataOutputStream dos) {
        HttpResponse httpResponse = createResponse(httpRequest);
        HttpResponseUtil.sendResponse(dos, httpResponse);
    }

}

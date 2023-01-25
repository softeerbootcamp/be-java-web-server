package Controller;

import Request.HttpRequest;
import Request.StatusCode;
import Response.HttpResponse;
import util.HttpResponseUtil;

import java.io.DataOutputStream;

public interface Controller {

    HttpResponse createResponse(HttpRequest httpRequest);

    default void response(HttpRequest httpRequest, DataOutputStream dos) {
        HttpResponse httpResponse = createResponse(httpRequest);
        HttpResponseUtil.sendResponse(dos, httpResponse);
    }

    default HttpResponse redirect(String loc, HttpRequest httpRequest) {
        HttpResponse response = HttpResponse.createResponse("", StatusCode.FOUND, httpRequest.getProtocol());
        response.putHeader("Location", loc);
        return response;
    }
}

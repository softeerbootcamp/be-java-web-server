package controller;

import request.HttpRequest;
import request.StatusCode;
import response.HttpResponse;
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

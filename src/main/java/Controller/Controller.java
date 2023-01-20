package Controller;

import Request.HttpRequest;
import Request.StatusCode;
import Response.HttpResponse;
import Response.HttpResponseBody;
import Response.HttpResponseHeaders;
import Response.HttpResponseStartLine;
import util.HttpResponseUtil;

import java.io.DataOutputStream;

public interface Controller {

    HttpResponse createResponse(HttpRequest httpRequest);

    default void response(HttpRequest httpRequest, DataOutputStream dos) {
        HttpResponse httpResponse = createResponse(httpRequest);
        HttpResponseUtil.sendResponse(dos, httpResponse);
    }

    default HttpResponse redirect(String loc, HttpRequest httpRequest) {
        HttpResponse response = new HttpResponse().startLine(new HttpResponseStartLine(StatusCode.FOUND, httpRequest.getProtocol()))
                .headers(new HttpResponseHeaders("", 0))
                .body(new HttpResponseBody("".getBytes()));
        response.putHeader("Location", loc);
        return response;
    }
}

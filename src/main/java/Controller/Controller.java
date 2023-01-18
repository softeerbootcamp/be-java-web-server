package Controller;

import Response.HttpResponse;
import util.HttpResponseUtil;

import java.io.DataOutputStream;

public interface Controller {

    HttpResponse createResponse();

    default void response(DataOutputStream dos) {
        HttpResponse httpResponse = createResponse();
        HttpResponseUtil.sendResponse(dos, httpResponse);
    }

}

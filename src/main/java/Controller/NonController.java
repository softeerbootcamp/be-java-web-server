package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import util.HttpResponseUtil;
import util.StatusCode;

import java.io.DataOutputStream;

public class NonController extends Controller{
    public NonController(HttpRequest httpRequest, DataOutputStream dos) {
        super(httpRequest, dos);
    }
    @Override
    public void response() {
        super.response();
        HttpResponse httpResponse = HttpResponse.createHttpResponse(httpRequest, StatusCode.NOT_FOUND);
        httpResponse.setHeaders(null);
        HttpResponseUtil.outResponse(dos, httpResponse);
    }
}

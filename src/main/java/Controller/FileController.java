package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import util.HttpResponseUtil;
import util.StatusCode;

import java.io.DataOutputStream;

public class FileController extends Controller{
    public FileController(HttpRequest httpRequest, DataOutputStream dos) {
        super(httpRequest, dos);
    }

    @Override
    public void response() {
        super.response();
        HttpResponse httpResponse = HttpResponse.createHttpResponse(this.httpRequest, StatusCode.OK);
        HttpResponseUtil.outResponse(this.dos, httpResponse);
    }
}

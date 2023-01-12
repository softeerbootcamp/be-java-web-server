package Controller;

import Request.HttpRequest;
import Response.HttpResponse;
import util.FileIoUtil;
import util.HttpResponseUtil;

import java.io.DataOutputStream;
import java.nio.file.Path;
import java.util.Objects;

public abstract class Controller {
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    DataOutputStream dos;

    public Controller(HttpRequest httpRequest, DataOutputStream dos) {
        this.httpRequest = httpRequest;
        this.dos = dos;
    }

    abstract public HttpResponse createResponse();

    public void response() {
        this.httpResponse = createResponse();
        HttpResponseUtil.outResponse(this.dos, this.httpResponse);
    }

}

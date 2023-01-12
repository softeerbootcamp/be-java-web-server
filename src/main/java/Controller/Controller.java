package Controller;

import Request.HttpRequest;
import Response.HttpResponse;

import java.io.DataOutputStream;

public abstract class Controller {
    HttpRequest httpRequest;
    HttpResponse httpResponse;
    DataOutputStream dos;

    public Controller(HttpRequest httpRequest, DataOutputStream dos) {
        this.httpRequest = httpRequest;
        this.dos = dos;
    }

    public void response() {

    }
}

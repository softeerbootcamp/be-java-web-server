package Controller;

import Request.HttpRequest;

import java.io.DataOutputStream;

public class NonController extends Controller{


    public NonController(HttpRequest httpRequest, DataOutputStream dos) {
        super(httpRequest, dos);
    }
}

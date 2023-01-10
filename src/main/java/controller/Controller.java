package controller;

import request.Request;
import response.Response;

import java.io.IOException;

public interface Controller {
    void selectedController(Request request, Response response) throws IOException;
}

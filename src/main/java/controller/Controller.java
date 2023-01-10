package controller;

import request.Request;
import response.Response;

public interface Controller {
    void selectedController(Request request, Response response);
}

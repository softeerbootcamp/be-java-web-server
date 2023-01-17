package controller;

import request.Request;
import response.NewResponse;
import response.Response;

import java.io.IOException;

public interface Controller {
    NewResponse controllerService(Request request) throws IOException;
}

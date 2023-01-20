package controller;

import request.Request;
import response.ResponseFactory;

import java.io.IOException;

public interface Controller {
    ResponseFactory controllerService(Request request) throws IOException;
}

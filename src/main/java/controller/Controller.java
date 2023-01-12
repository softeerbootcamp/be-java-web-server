package controller;

import model.request.Request;
import model.response.Response;

public interface Controller {
    Response getResponse(Request request);
}

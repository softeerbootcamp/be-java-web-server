package controller;

import model.request.RequestLine;
import model.response.Response;

public interface Controller {
    Response getResponse(RequestLine requestLine);
}

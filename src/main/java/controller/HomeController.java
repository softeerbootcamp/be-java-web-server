package controller;

import model.request.RequestLine;

public class HomeController {
    private final RequestLine requestLine;

    public HomeController(RequestLine requestLine) {
        this.requestLine = requestLine;
    }
}

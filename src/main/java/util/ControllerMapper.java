package controller;

import model.request.RequestLine;

public class ControllerMapper {
    public static Controller selectController(RequestLine requestLine) {
        if(requestLine.getControllerCriteria().equals("index.html")) {
            return new HomeController(requestLine);
        }

        return null;
    }
}

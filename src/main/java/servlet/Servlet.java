package servlet;

import request.HttpRequest;
import response.StatusCode;

public interface Servlet {
    StatusCode service(HttpRequest httpRequest);

    void get(HttpRequest httpRequest);

    StatusCode post(HttpRequest httpRequest);
}

package servlet;

import request.HttpRequest;
import response.StatusLine;

public interface Servlet {
    StatusLine service(HttpRequest httpRequest);

    void get(HttpRequest httpRequest);

    void post(HttpRequest httpRequest);
}

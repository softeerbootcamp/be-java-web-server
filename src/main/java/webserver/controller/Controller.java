package webserver.controller;

import webserver.domain.ModelAndView;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import java.io.IOException;

public interface Controller {

    void chain(Request req, Response res, ModelAndView mv) throws HttpRequestException, IOException;
}

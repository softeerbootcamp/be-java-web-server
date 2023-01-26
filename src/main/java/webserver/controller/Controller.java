package webserver.controller;

import webserver.view.ModelAndView;
import webserver.domain.request.Request;
import webserver.domain.response.Response;

public interface Controller {

    void chain(Request req, Response res, ModelAndView mv) ;
}

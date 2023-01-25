package webserver.controller;

import webserver.view.ModelAndView;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.io.IOException;

public class StaticController implements Controller {

    private StaticController (){}

    public static StaticController getInstance(){
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final StaticController INSTANCE = new StaticController();
    }
    @Override
    public void chain(Request req, Response res, ModelAndView mv) {
        ControllerInterceptor.executeController(getInstance(), req, res, mv);
        String path = req.getRequestLine().getResource().getPath();  //리소스 위치 경로
        mv.setViewPath(path);
    }
}

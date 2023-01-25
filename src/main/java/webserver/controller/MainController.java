package webserver.controller;

import db.BoardDatabase;
import webserver.annotation.ControllerInfo;
import webserver.domain.RequestMethod;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.view.ModelAndView;

public class MainController implements Controller{

    private MainController(){}

    public static MainController getInstance(){
        return MainController.LazyHolder.INSTANCE;
    }

    private static class LazyHolder{
        private static final MainController INSTANCE = new MainController();
    }

    @ControllerInfo(path = "/", methodName = "index", method = RequestMethod.GET)
    public void index(Response res, ModelAndView mv){
        mv.addViewModel("board", BoardDatabase.findAll());
        mv.setViewPath("/index.html");
    }
    @Override
    public void chain(Request req, Response res, ModelAndView mv) {
        ControllerInterceptor.executeController(getInstance(), req, res, mv);
    }
}

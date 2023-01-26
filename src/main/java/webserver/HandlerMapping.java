package webserver;

import webserver.controller.*;
import webserver.domain.request.Request;

import java.util.Map;

public class HandlerMapping {

    public static final Map<String, Controller> controllerMap = Map.of("/user", UserController.getInstance(), "/board", BoardController.getInstance());

    //find a proper controller to handle this request
    public Controller getHandler(Request req) {
        String path = req.getRequestLine().getResource().getPath();
        for(String key : controllerMap.keySet()){
            if(path.startsWith(key))
                return controllerMap.get(key);
        }
        return MainController.getInstance();
    }


    //return an instance of static controller
    public Controller getStaticHandler(){
        return StaticController.getInstance();
    }

}


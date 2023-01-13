package webserver;

import webserver.Controller.AuthController;
import webserver.Controller.Controller;
import webserver.Controller.StaticController;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;
import java.util.Map;


public class HandlerMapping {

    public static final Map<String, Controller> controllerMap = Map.of("/user", new AuthController());

    //find a proper controller to handle this request
    public Controller getHandler(Request req, Response res) {
        String path = req.getRequestLine().getResource().getPath();
        for(String key : controllerMap.keySet()){
            if(path.startsWith(key))
                return controllerMap.get(key);
        }
        return new StaticController();
    }


    //return an instance of static controller
    public Controller getStaticHandler(){
        return new StaticController();
    }

}


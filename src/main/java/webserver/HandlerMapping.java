package webserver;

import model.User;
import webserver.Controller.AuthController;
import webserver.Controller.Controller;
import webserver.Controller.StaticController;
import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static webserver.HandlerMapping.ControllerType.*;


public class HandlerMapping {

    private final Map<ControllerType, Controller> controllerMap = new EnumMap<ControllerType, Controller>(ControllerType.class);
    public HandlerMapping() {
        controllerMap.put(USER, new AuthController());
        controllerMap.put(STATIC, new StaticController());
    }

    public Controller getHandler(Request req) throws HttpRequestException{
        String path = req.getRequestLine().getResource().getPath();
        ControllerType controller = findController(path);
        return controllerMap.get(controller);
    }

    public Controller getStaticHandler(){
        return controllerMap.get(STATIC);
    }

    public enum ControllerType {

        USER("/user"),
        STATIC("/");

        private String routeName;

        private ControllerType(String routeName){
            this.routeName = routeName;
        }

        public String getRouteName(){
            return routeName;
        }

        public static ControllerType findController(String path){  //find controller that contains the path of given path variable
            return Arrays.stream(ControllerType.values())
                    .filter(controller -> path.startsWith(controller.getRouteName()))
                    .findFirst()
                    .orElseThrow(() -> new HttpRequestException(StatusCodes.NOT_FOUND));
        }

    }
}


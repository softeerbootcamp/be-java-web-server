package webserver;

import webserver.Controller.AuthController;
import webserver.Controller.Controller;
import webserver.domain.StatusCodes;
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
    }

    public Controller getHandler(String req) throws HttpRequestException{

        ControllerType controller = findController(req);
        return controllerMap.get(controller);

    }

    public enum ControllerType {

        USER("/user");

        private String routeName;

        private ControllerType(String routeName){
            this.routeName = routeName;
        }

        public String getRouteName(){
            return routeName;
        }

        public static ControllerType findController(String path){
            return Arrays.stream(ControllerType.values())
                    .filter(controller -> path.startsWith(controller.getRouteName()))
                    .findFirst()
                    .orElseThrow(() -> new HttpRequestException(StatusCodes.NOT_FOUND));
        }

    }
}


package webserver;

import webserver.Controller.AuthController;
import webserver.Controller.Controller;
import webserver.Controller.StaticController;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static webserver.HandlerMapping.ControllerType.AUTH;
import static webserver.HandlerMapping.ControllerType.findController;


public class HandlerMapping {

    private final Map<ControllerType, Controller> controllerMap = new EnumMap<ControllerType, Controller>(ControllerType.class);

    public HandlerMapping() {
        controllerMap.put(AUTH, new AuthController());
    }

    public Controller getHandler(String req) throws IOException {

        Optional<ControllerType> controller = findController(req);
        if (controller == null)
            return new StaticController();
        return controllerMap.get(controller);
    }

    public enum ControllerType {

        AUTH("/user/");

        private String routeName;

        private ControllerType(String routeName){
            this.routeName = routeName;
        }

        public String getRouteName(){
            return routeName;
        }

        public static Optional<ControllerType> findController(String path){
            return Arrays.stream(ControllerType.values())
                    .filter(controller -> path.startsWith(controller.getRouteName()))
                    .findFirst();
        }

    }
}


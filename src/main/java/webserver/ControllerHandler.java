package webserver;


import java.util.ArrayList;
import java.util.List;

public class ControllerHandler{

    private static List<Controller> controllers = new ArrayList<>();

    static {
        controllers.add(new UserController());
        controllers.add(new ViewController());
    }

}

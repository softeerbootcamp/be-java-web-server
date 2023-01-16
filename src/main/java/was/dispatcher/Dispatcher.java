package was.dispatcher;

import was.annotation.GetMapping;
import was.annotation.PostMapping;
import webserver.controller.Controller;
import webserver.controller.UserController;
import webserver.handler.ControllerHandler;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponseMessage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
public class Dispatcher implements ControllerHandler{
    private static Dispatcher dispatcher;

    private Dispatcher() {
    }

    public static Dispatcher getInstance() {
        if (dispatcher != null) {
            return dispatcher;
        }
        return dispatcher = new Dispatcher();
    }
    @Override
    public HttpResponseMessage handle(HttpRequest httpRequest) {
        Controller controller = new UserController();
        String path = httpRequest.getRequestLine().getUrl();
        if (path.contains("user")) {
            controller = new UserController();
        }
        return dispatch(httpRequest, controller, path);
    }
    public HttpResponseMessage dispatch(HttpRequest httpRequest, Controller controller, String path) {
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(PostMapping.class);
            if (annotation == null) {
                continue;
            }
            PostMapping postMapping = (PostMapping) annotation;
            Object[] parameter = new Object[1];
            parameter[0] = httpRequest;
            if (path.equals(postMapping.value())) {
                try{
                    return (HttpResponseMessage) method.invoke(controller, parameter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //TODO 오류처리 해야함
        return null;
    }
}

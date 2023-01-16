package was.dispatcher;

import was.annotation.GetMapping;
import webserver.controller.Controller;
import webserver.controller.UserController;
import webserver.handler.ControllerHandler;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponseMessage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
public class Dispatcher implements ControllerHandler{
    private HttpRequest httpRequest;

    public Dispatcher(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponseMessage handle() {
        Controller controller = new UserController();
        String path = httpRequest.getRequestLine().getUrl();
        if (path.contains("user")) {
            controller = new UserController();
        }
        return dispatch(controller, path);
    }
    public HttpResponseMessage dispatch(Controller controller, String path) {
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(GetMapping.class);
            if (annotation == null) {
                continue;
            }
            GetMapping getMapping = (GetMapping) annotation;
            Object[] parameter = new Object[1];
            parameter[0] = httpRequest;
            if (path.equals(getMapping.value())) {
                try{
                    System.out.println("여기까진 오나?");
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

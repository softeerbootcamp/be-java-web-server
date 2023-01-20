package was.dispatcher;

import enums.HttpMethod;
import was.annotation.RequestMapping;
import was.controller.Controller;
import was.controller.ControllerFactory;
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
        String path = httpRequest.getRequestLine().getUrl();
        return dispatch(httpRequest, ControllerFactory.getControllerInstance(httpRequest), path);
    }
    private HttpResponseMessage dispatch(HttpRequest httpRequest, Controller controller, String path) {
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(RequestMapping.class);
            if (annotation == null) {
                continue;
            }

            RequestMapping requestMappingAnno = (RequestMapping) annotation;
            HttpMethod requestMethod = httpRequest.getRequestLine().getHttpMethod();
            System.out.println("path = " + path);
            System.out.println("requestMappingAnno = " + requestMappingAnno.value());
            if (requestMappingAnno.method() == requestMethod && path.equals(requestMappingAnno.value())) {
                try{
                    Object[] parameter = new Object[1];
                    parameter[0] = httpRequest;
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

package was.dispatcher;

import enums.HttpMethod;
import was.annotation.Auth;
import was.annotation.RequestMapping;
import was.controller.Controller;
import was.controller.ControllerFactory;
import was.interceptor.AuthInterceptor;
import webserver.domain.HttpResponse;
import webserver.handler.ControllerHandler;
import webserver.domain.HttpRequest;
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
    public HttpResponse handle(HttpRequest httpRequest) {
        String path = httpRequest.getRequestLine().getUrl();
        return dispatch(httpRequest, ControllerFactory.getControllerInstance(httpRequest), path);
    }
    private HttpResponse dispatch(HttpRequest httpRequest, Controller controller, String path) {
        Method[] methods = controller.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation auth = method.getAnnotation(Auth.class);
            if (auth != null) {
                if (!AuthInterceptor.checkAuth(httpRequest)) {
                    HttpResponse httpResponse = new HttpResponse();
                    httpResponse.sendRedirect("/user/login.html");
                    return httpResponse;
                }
            }
            Annotation annotation = method.getAnnotation(RequestMapping.class);
            if (annotation == null) {
                continue;
            }

            RequestMapping requestMappingAnno = (RequestMapping) annotation;
            HttpMethod requestMethod = httpRequest.getRequestLine().getHttpMethod();
            if (requestMappingAnno.method() == requestMethod && path.equals(requestMappingAnno.value())) {
                try{
                    Object[] parameter = new Object[1];
                    parameter[0] = httpRequest;
                    return (HttpResponse) method.invoke(controller, parameter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //TODO 오류처리 해야함
        return null;
    }
}

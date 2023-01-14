package was.dispatcher;

import was.annotation.GetMapping;
import webserver.controller.ControllerHandler;
import webserver.controller.QueryStringHandler;
import webserver.domain.HttpResponseMessage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Dispatcher {
    public HttpResponseMessage dispatch(ControllerHandler controllerHandler, String path) {
        Method[] methods = controllerHandler.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(GetMapping.class);
            if (annotation == null) {
                continue;
            }
            GetMapping getMapping = (GetMapping) annotation;
            if (path.equals(getMapping.value())) {
                try{
                    return (HttpResponseMessage) method.invoke(controllerHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //TODO 오류처리 해야함
        return null;
    }
}

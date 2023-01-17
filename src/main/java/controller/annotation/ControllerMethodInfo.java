package controller.annotation;


import util.HttpMethod;
import util.RequestDataType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerMethodInfo {
    String path();
    RequestDataType type();

    HttpMethod method();



}
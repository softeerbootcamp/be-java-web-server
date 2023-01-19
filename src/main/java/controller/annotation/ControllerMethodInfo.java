package controller.annotation;


import util.HttpMethod;
import request.RequestDataType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerMethodInfo {
    String path();

    HttpMethod method();



}
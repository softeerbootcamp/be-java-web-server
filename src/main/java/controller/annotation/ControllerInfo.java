package controller.annotation;


import util.HttpMethod;
import util.UrlType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerInfo {
    String path();

    UrlType u();

    HttpMethod method();



}
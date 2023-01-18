package webserver.service;

import webserver.httpUtils.Request;

public interface Service {
    default void exec(Request req){};
}

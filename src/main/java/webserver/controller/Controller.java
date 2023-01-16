package webserver.controller;

import webserver.domain.request.URI;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.io.IOException;
import java.util.Map;

public interface Controller {

    void chain(Request req, Response res) throws HttpRequestException, IOException;
}

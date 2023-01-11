package webserver.Controller;

import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.io.IOException;
import java.io.OutputStream;

public interface Controller {

    void handle(String request, Response res) throws HttpRequestException;
}

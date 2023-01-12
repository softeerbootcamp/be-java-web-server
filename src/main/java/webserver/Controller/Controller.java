package webserver.Controller;

import webserver.domain.request.URI;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.exception.HttpRequestException;

import java.io.IOException;
import java.util.Map;

public interface Controller {
    default void handle(Request req, Response res) throws HttpRequestException, IOException{
        URI resource = req.getRequestLine().getResource();
        chain(resource.getPath(), resource.getQueryString(), res);
    }
    void chain(String path, Map<String, String> queryString, Response res) throws IOException;
}

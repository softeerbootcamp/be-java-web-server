package controller;

import filesystem.FileSystem;
import io.request.HttpRequest;
import io.response.FindResult;
import io.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(FacadeController.class);

    private final FileSystem fileSystem = new FileSystem();

    @Override
    public void handle(HttpRequest request, Response response) {
        logger.info("user controller called");
        if (request.isStaticResourceRequest()) {
            handleStaticResourceRequest(request, response);
        }
        handleRequest(request, response);
    }

    private void handleRequest(HttpRequest request, Response response) {
        Map<String, String> allQuery = request.getAllQuery();
        for (String s : allQuery.keySet()) {
            System.out.println(allQuery.get(s));
        }
    }

    private void handleStaticResourceRequest(HttpRequest request, Response response) {
        FindResult findResult = fileSystem.findResource(request.getUrl());
        response.update(findResult);
        response.send();
    }
}

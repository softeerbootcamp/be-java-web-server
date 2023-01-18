package controller;

import filesystem.FileSystem;
import filesystem.FindResource;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        logger.debug("main controller called");
        FindResource findResult = FileSystem.findResource(request.getUrl());
        response.update(findResult);
        response.send();
    }
}

package controller;

import filesystem.FileSystem;
import io.request.HttpRequest;
import io.response.FindResult;
import io.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        logger.debug("main controller called");
        FindResult findResult = FileSystem.findResource(request.getUrl());
        response.update(findResult);
        response.send();
    }
}

package controller;

import filesystem.FileSystem;
import io.request.HttpRequest;
import io.response.FindResult;
import io.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final FileSystem fileSystem = new FileSystem();

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        logger.info("main controller called");
        FindResult findResult = fileSystem.findResource(request.getUrl());
        response.update(findResult);
        response.send();
    }
}

package controller;

import filesystem.FileSystem;
import io.request.HttpRequest;
import io.response.FindResult;
import io.response.HttpResponse;

public class MainController implements Controller {

    private final FileSystem fileSystem = new FileSystem();

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        FindResult findResult = fileSystem.findResource(request.getUrl());
        response.update(findResult);
        response.send();
    }
}

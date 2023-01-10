package controller;

import dto.UserInfoDTO;
import filesystem.FileSystem;
import io.request.HttpRequest;
import io.request.PathParser;
import io.response.FindResult;
import io.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;

public class UserController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final FileSystem fileSystem = new FileSystem();
    private final UserService userService = new UserService();
    private final PathParser pathParser = new PathParser();

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        logger.info("user controller called");
        if (request.isStaticResourceRequest()) {
            handleStaticResourceRequest(request, response);
            return;
        }
        handleRequest(request, response);
    }

    private void handleRequest(HttpRequest request, HttpResponse response) {
        UserInfoDTO userInfo = new UserInfoDTO(request.getAllQuery());
        userService.signIn(userInfo);
        response.redirect(pathParser.getIndexPageUrl());
    }

    private void handleStaticResourceRequest(HttpRequest request, HttpResponse response) {
        FindResult findResult = fileSystem.findResource(request.getUrl());
        response.update(findResult);
        response.send();
    }
}

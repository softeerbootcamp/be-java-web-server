package controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.HttpRequest;
import response.HttpResponse;
import service.UserService;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class LoginController extends AbstractController{

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String lineSeparator = System.lineSeparator();

    @Override
    public HttpResponse get(HttpRequest httpRequest) {
        return null;
    }

    @Override
    protected HttpResponse post(HttpRequest httpRequest) throws IOException, URISyntaxException {
        UserService userService = UserService.from(httpRequest);
        HttpResponse httpResponse = userService.postLoginService();

        logger.debug("HttpResponse Header : {}{}{}{}{}{}", lineSeparator,
                httpResponse.getStatusLine().getValue(),lineSeparator,
                httpResponse.getHeader(),lineSeparator,
                httpResponse.getCookie());
        ;
        return httpResponse;
    }
}

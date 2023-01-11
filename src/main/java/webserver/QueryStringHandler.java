package webserver;

import service.UserService;
import util.HttpParser;

import java.util.Map;

public class QueryStringHandler implements ControllerHandler{
    private final HttpRequest httpRequest;

    public QueryStringHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public HttpResponse handle() {
        String url = httpRequest.getRequestURL();
        Map map = HttpParser.parseQueryString(url);
        UserService userService = new UserService();
        userService.addUser(map);
        return null;
    }
}

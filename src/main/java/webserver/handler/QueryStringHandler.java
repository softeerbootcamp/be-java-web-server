package webserver.handler;

import service.UserService;
import util.HttpParser;
import was.annotation.GetMapping;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpResponseMessage;

import java.util.Map;

public class QueryStringHandler implements ControllerHandler {
    private final HttpRequest httpRequest;

    public QueryStringHandler(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
    @Override
    public HttpResponseMessage handle() {
        String url = httpRequest.getRequestURL();
        Map<String, String> map = HttpParser.parseQueryString(url);
        UserService userService = new UserService();
        userService.addUser(map);

        HttpResponse httpResponse = new HttpResponse();
        return new HttpResponseMessage(httpResponse.sendRedirect("/index.html"), httpResponse.getBody());
    }
}

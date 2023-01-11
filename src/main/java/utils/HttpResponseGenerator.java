package utils;

import http.repsonse.HttpResponse;
import http.request.HttpRequest;
import model.User;
import service.UserService;

import java.util.Map;

public class HttpResponseGenerator {
    public static HttpResponse generateResponse(HttpRequest httpRequest, UserService userService) {
        if (checkParams(httpRequest, userService)) {
            return new HttpResponse(httpRequest.getVersion(), StatusCode.FOUND, null, null);
        }
        return new HttpResponse(httpRequest.getVersion(), StatusCode.OK,
                HttpUtils.setContentType(httpRequest.getContentType()), FileIoUtils.loadFile(httpRequest.getRequestTarget()));
    }

    private static boolean checkParams(HttpRequest httpRequest, UserService userService) {
        if (httpRequest.hasParams()) {
            String target = httpRequest.getRequestTarget();
            String[] params = target.split("\\?");
            Map<String, String> map = HttpUtils.parseQuerystring(params[1]);
            User user = new User(map.get("userId"), map.get("password"), map.get("name"), map.get("email"));
            userService.join(user);
            return true;
        }
        return false;
    }
}

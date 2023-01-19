package webserver.service;

import webserver.httpUtils.Request;
import webserver.httpUtils.Response;

import java.util.Map;

public class LogInService implements Service{
    @Override
    public Response exec(Request req) {
        Map<String, String> userInfo = getUserInfo(req);



        return null;
    }
}

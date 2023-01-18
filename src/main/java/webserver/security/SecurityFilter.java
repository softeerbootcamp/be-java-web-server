package webserver.security;

import org.checkerframework.checker.units.qual.A;
import webserver.domain.request.Request;
import webserver.domain.response.Response;

import java.util.ArrayList;
import java.util.List;

public class SecurityFilter {

    private static List<String> accessList;

    static{
        accessList = List.of("user/list");
    }

    public void checkAuth(Request req, Response res){

    }

}

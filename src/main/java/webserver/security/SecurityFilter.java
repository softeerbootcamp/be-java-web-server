package webserver.security;

import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.util.List;

public class SecurityFilter {

    public static List<String> accessList;

    static{
        accessList = List.of("/user/list");
    }

    public static void checkAuthorization(String path){
        if(accessList.stream().anyMatch(path::startsWith)){
            if(SecurityContext.getContext() == null)
                throw new HttpRequestException(StatusCodes.FORBIDDEN, "<script>alert('권한이 없습니다.'); window.location.href = 'http://localhost:8080/user/login.html';</script>");
        }
    }

}

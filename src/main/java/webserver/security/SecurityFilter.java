package webserver.security;

import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.util.List;

public class SecurityFilter {

    public static List<String> accessList;

    static{
        accessList = List.of("/user/list", "/board/create", "/board/form.html");
    }

    public static void checkAuthorization(String path) {
        if(accessList.stream().anyMatch(path::startsWith) && SecurityContext.getContext() == null){
                throw HttpRequestException.builder()
                        .statusCode(StatusCodes.FORBIDDEN)
                        .msg("<script>alert('권한이 없습니다.'); window.location.href = 'http://localhost:8080/user/login.html';</script>")
                        .build();
        }
    }

}

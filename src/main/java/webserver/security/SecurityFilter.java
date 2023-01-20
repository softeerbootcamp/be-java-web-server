package webserver.security;

import webserver.domain.ModelAndView;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.util.List;

public class SecurityFilter {

    public static List<String> accessList;

    static{
        accessList = List.of("/user/list");
    }

    public static void checkAuthorization(String path , ModelAndView mv){
        if(accessList.stream().anyMatch(path::startsWith)){
            if(mv.getViewModel().get("login").toString().equals("false"))
                throw new HttpRequestException(StatusCodes.FORBIDDEN, "<script>alert('권한이 없습니다.'); window.location.href = 'http://localhost:8080/user/login.html';</script>");
        }
    }

}

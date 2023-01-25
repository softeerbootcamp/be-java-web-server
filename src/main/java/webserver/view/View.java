package webserver.view;

import webserver.domain.StatusCodes;
import webserver.domain.request.Request;
import webserver.domain.response.Response;
import webserver.security.SecurityContext;
import webserver.utils.HttpSessionUtils;

import java.io.IOException;

public interface View {

    default void makeView(Request req, Response res, ModelAndView mv) throws IOException {

        if(mv.getViewPath().startsWith("redirect:"))  //separate a redirection request from a rendering request
        {
            res.redirect(StatusCodes.SEE_OTHER, mv.getViewPath().substring(9));
            return;
        }
        String menuBar = "";
        String nameTag = "";
        if(SecurityContext.getContext() == null){
            //if a user has not logged in to the system
            menuBar = "<li><a href=\"../user/logout\" role=\"button\">로그아웃</a></li>\n" +
                    "                <li><a href=\"#\" role=\"button\">개인정보수정</a></li>";
        }else{
            //if a user has logged in
            menuBar = "<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>\n" +
                    "                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>";
            String username = HttpSessionUtils.sessionIdToUserName(mv.getViewModel().get("session-id").toString());
            nameTag = "<li id=\"username\"><a href=\"#\">" + username +"님 </a></li>";
        }

        render(req, res, mv, menuBar, nameTag);
    }

    void render(Request req, Response res, ModelAndView mv, String menuBar, String nameTag) throws IOException;

}

package view;

import enums.ContentType;
import enums.HttpStatus;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import session.HttpSessionManager;
import utils.FileIoUtils;


public class IndexView implements View{
    private static final String COOKIE_SESSION_KEY = "sid";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";


    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) {

        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        if(isLogined(request)){ //로그인 상태인 경우 사용자 이름 표시, 로그인 버튼 없음
            String sessionKey = request.getCookie(COOKIE_SESSION_KEY);
            User user = HttpSessionManager.getSession(sessionKey).getUser();
            body = showUserName(user,body).getBytes();
        }else{ //로그인 상태가 아닐 경우 그냥 그대로
            body = showLoginButton(body).getBytes();
        }

        response.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
        response.setBody(body);//body에는 요청한 파일 내용이 들어감
    }

    public boolean isLogined(HttpRequest request){
        System.out.println("isLogined");
        String sessionKey = request.getCookie(COOKIE_SESSION_KEY);
        System.out.println("isLogined session key: "+sessionKey);
        // 쿠키 값이 유효한 경우 == 로그인 된 경우
        if (sessionKey != null && HttpSessionManager.getSession(sessionKey) != null) {
            return true;
        }
        return false;
    }

    private static String showUserName(User user,byte[] bytes){
        StringBuilder content = new StringBuilder();
        content.append("<li>")
                .append("<a href=\"#\">")
                .append(user.getUserId())
                .append("</a>")
                .append("</li>");

        String html = new String(bytes);
        html = html.replace("<!--<li> 로그인 시 유저 네임 </li>-->",content);
        return html;
    }

    private static String showLoginButton(byte[] bytes){
        StringBuilder content = new StringBuilder();
        content.append("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>");

        String html = new String(bytes);
        html = html.replace("<!--<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>-->",content);
        return html;

    }
}

package view;

import enums.ContentType;
import enums.HttpStatus;
import model.User;
import request.HttpRequest;
import response.HttpResponse;
import session.HttpSessionManager;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


public class IndexView implements View{
    private static final String COOKIE_SESSION_KEY = "sid";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";


    @Override
    public void render(HttpRequest request, HttpResponse response, Model data) throws IOException, URISyntaxException {

        response.setStatus(HttpStatus.OK);
        response.setContentType(ContentType.HTML);
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        if(isLogined(request)){ //로그인 상태인 경우 사용자 이름 표시, 로그인 버튼 없음
            String sessionKey = request.getCookie(COOKIE_SESSION_KEY);
            User user = HttpSessionManager.getSession(sessionKey).getUser();
            List<Map<String,String>> qnas = (List<Map<String,String>>) data.getAttribute("qna_data");
            body = showUserName(user,body).getBytes();
            body = showQuestions(body,qnas).getBytes();
            body = showPostingButton(body).getBytes();
        }else{ //로그인 상태가 아닐 경우 그냥 그대로
            body = showLoginButton(body).getBytes();
        }
        List<Map<String,String>> qnas = (List<Map<String,String>>) data.getAttribute("qna_data");
        body = showQuestions(body,qnas).getBytes();

        response.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
        response.setBody(body);//body에는 요청한 파일 내용이 들어감
    }

    public boolean isLogined(HttpRequest request){
        String sessionKey = request.getCookie(COOKIE_SESSION_KEY);
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

    private static String showQuestions(byte[] bytes,List<Map<String,String>> qnas){
        StringBuilder content = new StringBuilder();

        for(Map<String,String> qna : qnas){
            content.append("<li>\n" +
                    "                  <div class=\"wrap\">\n" +
                    "                      <div class=\"main\">\n" +
                    "                          <strong class=\"subject\">\n" +
                    "                              <a href=\"./qna/show.html\">"+qna.get("title")+"</a>\n" +
                    "                          </strong>\n" +
                    "                          <div class=\"auth-info\">\n" +
                    "                              <i class=\"icon-add-comment\"></i>\n" +
                    "                              <span class=\"time\">"+qna.get("time")+"</span>\n" +
                    "                              <a href=\"./user/profile.html\" class=\"author\">"+qna.get("writer")+"</a>\n" +
                    "                          </div>\n" +
                    "                          <div class=\"reply\" title=\"댓글\">\n" +
                    "                              <i class=\"icon-reply\"></i>\n" +
                    "                              <span class=\"point\">"+qna.get("row_id")+"</span>\n" +
                    "                          </div>\n" +
                    "                      </div>\n" +
                    "                  </div>\n" +
                    "              </li>");
        }

        String html = new String(bytes);
        html = html.replace("<!--please add here -->",content);
        return html;
    }

    private static String showPostingButton(byte[] bytes){
        StringBuilder content = new StringBuilder();
        content.append("<a href=\"./qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>");

        String html = new String(bytes);
        html = html.replace("<!--<a href=\"./qna/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">질문하기</a>-->\n",content);
        return html;
    }


}

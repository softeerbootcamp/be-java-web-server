package webserver;

import controller.NotFoundExceptionHandler;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class RequestDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_DIR = "./templates";
    private static final String STATIC_DIR = "./static";
    private static final String CONTENT_LENGTH_HEADER_KEY = "Content-Length";
    private static final String COOKIE_SESSION_KEY = "sid";
    private static final String MESSAGE_UNSUPPORTED_EXTENSION = "지원되지 않는 확장자 입니다.";
    private static final String EXTENSION_DELIMITER = "\\.";

    /**
     * 정적 파일 반환
     * @param request
     * @param response
     * @throws NullPointerException
     * @throws IOException
     * @throws URISyntaxException
     */

    public static void handle(HttpRequest request, HttpResponse response) throws NullPointerException, IOException, URISyntaxException {
        String url = request.getUrl();
        ContentType type = extractExtension(url);
        boolean isTemplate = type.isTemplateDir(); //template 디렉토리 하위인지 확인
        if(!isTemplate) serveFile(STATIC_DIR + url,request, response);
        else if (isTemplate) serveFile(TEMPLATES_DIR + url, request,response);
    }

    public static boolean isLogined(HttpRequest request){
        System.out.println("isLogined");
        String sessionKey = request.getCookie(COOKIE_SESSION_KEY);
        System.out.println("isLogined session key: "+sessionKey);
        // 쿠키 값이 유효한 경우 == 로그인 된 경우
        if (sessionKey != null && HttpSessionManager.getSession(sessionKey) != null) {
            return true;
        }
        return false;
    }

    /**
     * 파싱한 url에 해당하는 파일을 파라미터의 HttpResponse에 헤더와 함께 담음
     * @param url
     * @param res
     */
    private static void serveFile(String url, HttpRequest req,HttpResponse res) throws NullPointerException, IOException, URISyntaxException {
        ContentType contentType = extractExtension(url);
        res.setStatus(HttpStatus.OK);
        res.setContentType(contentType);
        System.out.println("serveFile url: "+url);
        //TODO 사용자가 로그인 상태일 경우 /index.html에서 사용자 이름을 표시해 준다. 아니라면 그냥 index.html 그대로
        //TODO 사용자가 로그인 상태가 아닐 경우 /index.html에서 [로그인] 버튼을 표시해 준다. 맞다면 로그인 표시 없애기
        byte[] body = FileIoUtils.loadFileFromClasspath(url);
        if(url.equals("./templates/index.html")){
            if(isLogined(req)){ //로그인 상태인 경우 사용자 이름 표시, 로그인 버튼 없음
                String sessionKey = req.getCookie(COOKIE_SESSION_KEY);
                User user = HttpSessionManager.getSession(sessionKey).getUser();
                body = showUserName(user,body).getBytes();
                res.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
                res.setBody(body);
            }else{ // 로그인 상태가 아니면 로그인 버튼 보이도록
                body = showLoginButton(body).getBytes();
                res.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
                res.setBody(body);
            }
        }
        res.addToHeader(CONTENT_LENGTH_HEADER_KEY, String.valueOf(body.length));
        res.setBody(body);//body에는 요청한 파일 내용이 들어감
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

    private static ContentType extractExtension(String url) {
        String[] tokens = url.split(EXTENSION_DELIMITER);
        return ContentType.fromExtension(tokens[tokens.length - 1])
                .orElseThrow(() -> new IllegalArgumentException(MESSAGE_UNSUPPORTED_EXTENSION));
    }

}

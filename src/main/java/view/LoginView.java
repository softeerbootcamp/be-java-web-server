package view;

import util.FileUtils;

import java.io.IOException;

public class LoginView {

    public static String render(String name, String url, String body) throws IOException {

        String userName = "<li><a role=\"button\">사용자 이름: " + name + "</a></li>";

        if (body.length() == 0) {
            byte[] file = FileUtils.loadFile(url);
            body = new String(file);
        }

        body = body.replace("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>", userName);
        body = body.replace("<li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>", "");

        return body.replace("<!-- 메모장 글 쓰기 버튼 구현 -->", "<a href=\"./memo/form.html\" class=\"btn btn-primary pull-right\" role=\"button\">글 쓰기</a>");
    }
}

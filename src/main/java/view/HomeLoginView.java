package view;

import util.FileUtils;

public class HomeLoginView {

    public static String render(String name, String url) {
        byte[] file = FileUtils.loadFile(url);

        String userName = "<li><a role=\"button\">사용자 이름: " + name + "</a></li>";

        String htmlFile = new String(file);
        htmlFile = htmlFile.replace("<li><a href=\"../user/login.html\" role=\"button\">로그인</a></li>\n" +
                "                <li><a href=\"../user/form.html\" role=\"button\">회원가입</a></li>", userName);

        return htmlFile;
    }
}

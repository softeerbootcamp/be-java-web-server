package was.view;

import java.io.BufferedReader;
import java.io.IOException;

public class LoginIndexCallback implements BufferedReaderCallback{
    @Override
    public String createDynamicHtmlWith(String line, String userId) {
        if (line.contains("로그인") || line.contains("회원가입")) {
            return "";
        }
        if (line.contains("class=\"dropdown-toggle\"")) {
            return ("<a>" + userId + "님</a></li><li>") + line;
        }
        if (line.contains("Posts")) {
            return line + "<li><a href=\"#\" role=\"button\">개인정보수정</a></li>" +
                    "<li><a href=\"../user/logout\" role=\"button\">로그아웃</a></li>";
        }
        return line;
    }

}

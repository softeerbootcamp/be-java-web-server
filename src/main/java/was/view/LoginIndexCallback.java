package was.view;

import java.io.BufferedReader;
import java.io.IOException;

public class LoginIndexCallback implements BufferedReaderCallback{
    @Override
    public String createDynamicHtmlWith(BufferedReader br, String userId) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.contains("로그인") || line.contains("회원가입")) {
                continue;
            }
            if (line.contains("class=\"dropdown-toggle\"")) {
                sb.append("<a>" + userId + "님</a></li><li>");
            }
            sb.append(line);
            if (line.contains("Posts")) {
                sb.append("<li><a href=\"#\" role=\"button\">개인정보수정</a></li>");
                sb.append("<li><a href=\"../user/logout\" role=\"button\">로그아웃</a></li>");
            }
        }
        return sb.toString();
    }

}

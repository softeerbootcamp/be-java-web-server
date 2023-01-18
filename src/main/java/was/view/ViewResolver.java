package was.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ViewResolver {
    public static byte[] makeDynamicHtml(String userId, String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
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
            }
            return sb.toString().getBytes();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

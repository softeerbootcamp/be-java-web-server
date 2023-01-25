package was.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ViewTemplate {
    public static byte[] fileReadTemplate(String userId, String path, BufferedReaderCallback callback) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(callback.createDynamicHtmlWith(line, userId));
            }
            return sb.toString().getBytes();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package was.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ViewTemplate {
    public static byte[] fileReadTemplate(String userId, String path, BufferedReaderCallback callback) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            return callback.createDynamicHtmlWith(br, userId).getBytes();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

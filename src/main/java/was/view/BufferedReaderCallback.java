package was.view;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {

    String createDynamicHtmlWith(BufferedReader br, String userId) throws IOException;
}

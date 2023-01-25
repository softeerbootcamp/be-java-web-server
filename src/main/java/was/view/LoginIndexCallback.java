package was.view;

import java.io.BufferedReader;
import java.io.IOException;

public class LoginIndexCallback implements BufferedReaderCallback{
    @Override
    public String createDynamicHtmlWith(String line) {
        return line;
    }

}

package util;

import java.io.BufferedReader;
import java.io.IOException;

public class Descriptor {

    private Descriptor() {
    }

    public static String getWholeMessage(BufferedReader br) throws IOException {
        String msg = "";
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            msg += line + "\n\r";
        }

        return msg;
    }
}

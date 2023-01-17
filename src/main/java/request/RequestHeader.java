package request;

import java.util.ArrayList;
import java.util.List;

public class RequestHeader {
    private static List<String> headerLines = new ArrayList<>();

    public void addHeaderLines(String oneLine){
        headerLines.add(oneLine);
    }

}


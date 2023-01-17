package request;

import java.util.ArrayList;
import java.util.List;

public class RequestBody {
    private static List<String> bodyLines= new ArrayList<>();

    public void addBodyLines(String oneLine){
        bodyLines.add(oneLine);
    }

    public static List<String> getBodyLines() {
        return bodyLines;
    }
}

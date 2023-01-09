package util;

public class HttpRequestUtil {
    public static String getUrl(String line) {
        String[] lineSplit = line.split(" ");
        return lineSplit[1];
    }
}

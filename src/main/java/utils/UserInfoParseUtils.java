package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserInfoParseUtils {
    public static List<String> parseUrlToGetUserInfo(List<String> requestBodyLine) {
        String result = getUserInfoFromBodyLines(requestBodyLine);
        List<String> parsedUserInfo = new ArrayList<>();
        String[] unParsedUserInfos = result.split("&");
        for (String eachInfo : unParsedUserInfos) {
            parsedUserInfo.add(eachInfo.split("=")[1]);
        }
        return parsedUserInfo;
    }
    public static String getUserInfoFromBodyLines(List<String> lines){
        for (String line:lines
        ) {
            if (line.contains("user")) return line;
        }
        return null;
    }
    public static List<String> userInfoDecoder(List<String> userInfos) {
        List<String> decodedInfos = new ArrayList<>();
        for(String info : userInfos){
            decodedInfos.add(URLDecoder.decode(info, StandardCharsets.UTF_8));
        }
        return decodedInfos;
    }

}

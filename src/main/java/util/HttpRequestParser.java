package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequestParser {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);
    public static Path mappingPath(String path) {
        String dir = "./src/main/resources/";
        String ex = findExtension(path);
        String targetDir = checkExtension(ex) ? "templates" : "static";
        return new File(dir+targetDir+path).toPath();
    }
    public static String findExtension(String path){
        String[] temp = path.split("\\.");
        String extension = temp[temp.length - 1];
        return extension;
    }
    public static boolean checkExtension(String ex){
        return ex.equals("html") || ex.equals("ico");
    }
    public static Map<String,String> parseQuery(String query){
        Map<String, String> querys = new LinkedHashMap<>();
        String[] params = query.split("&");
        for(String param: params){
            String[] temp = param.split("=");
            querys.put(URLDecoder.decode(temp[0]), URLDecoder.decode(temp[1]));
            logger.debug("param: "+temp[0]+' '+temp[1]);
        }
        return querys;
    }

}

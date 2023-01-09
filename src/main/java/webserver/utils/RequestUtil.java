package webserver.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    public static String[] parseRequestedLine(String req){
        String [] parsedReq = req.split(" ");
        return parsedReq;
    }

    public static String[] parseRequestedHeader(String req){
        String [] parsedReq = req.split(" ", 2);
        return parsedReq;
    }

    public static void readHttpRequest(Map<String, String> map){
        for (String key: map.keySet()){
            System.out.println(key+ " = " + map.get(key));
        }
    }


    public static Map<String, String> parseHttpRequest(InputStream in) throws IOException {
        Map<String, String> reqMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        //Store Http Request Request Line into HashMap
        String line = br.readLine();
        reqMap.put("Header: ", line);

        //Store Http Request header into HashMap
        while(!line.equals("")){
            String[] parsedReq = parseRequestedHeader(line);
            reqMap.put(parsedReq[0], parsedReq[1]);
            line = br.readLine();
        }
        return  reqMap;
    }

}

package webserver.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RequestParser {

    public static String[] parseRequestedStatic(String req){
        String [] parsedReq = req.split(" ");
        return parsedReq;
    }

    public static String[] parseRequestedHeader(String req){
        String [] parsedReq = req.split(" ", 2);
        return parsedReq;
    }



}

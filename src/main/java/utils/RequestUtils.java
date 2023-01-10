package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestUtils {
    private static final int File_Line_Index = 1;

    public static String getFilePathByRequest(String requestLine){

        System.out.println("firstLine : "+requestLine);
        String[] filePath = requestLine.split(" ");
        return filePath[File_Line_Index];
    }
    public static void printRequestLines(String requestLine, BufferedReader bufferedReader) throws IOException {
        while (!requestLine.equals("")){
            requestLine = bufferedReader.readLine();
            System.out.println("request : "+requestLine);
        }
    }
}

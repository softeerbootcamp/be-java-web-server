package utils;

public class RequestUtils {
    private static final int File_Line_Index = 1;

    public static String getFilePathByRequest(String requestLine){

        String[] filePath = requestLine.split(" ");
        return filePath[File_Line_Index];
    }
}

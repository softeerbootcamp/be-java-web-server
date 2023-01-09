package webserver.utils;

import webserver.exception.NoFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static webserver.utils.RequestUtil.parseRequestedLine;

public class FilePathParser {

    private static final String pathStatic = "/static";
    private static final String pathTemplates = "/templates";

    public static byte[] getStaticFilesPath(String fileName) throws IOException {
        String fileUrl = FilePathParser.class.getResource(pathStatic)  + fileName;
        fileUrl = fileUrl.substring(5);
        File file = new File(fileUrl);
        if(!file.isFile())
            throw new NoFileException();
        byte[] body = Files.readAllBytes(new File(fileUrl).toPath() );
        return body;
    }

    public static byte[] getTemplateFilesPath(String fileName) throws IOException {
        String fileUrl = FilePathParser.class.getResource(pathTemplates)  + fileName;
        fileUrl = fileUrl.substring(5);
        File file = new File(fileUrl);
        if(!file.isFile())
            throw new NoFileException();
        byte[] body = Files.readAllBytes(new File(fileUrl).toPath() );
        return body;
    }

    public static byte[] staticFileResolver(String fileName) throws IOException, NoFileException {
        String[] type = fileName.split("\\.");
        String typeName = type[type.length - 1];
        if(typeName.equals("html") || typeName.equals("ico"))
            return getTemplateFilesPath(fileName);
        else
            return getStaticFilesPath(fileName);
    }

    public static String findFilePath(Map<String, String> map){
        String requestLine = map.get("Header: ");
        return parseRequestedLine(requestLine)[1];
    }
}

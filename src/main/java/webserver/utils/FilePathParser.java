package webserver.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FilePathParser {

    private static final String pathStatic = "/static";
    private static final String pathTemplates = "/templates";

    public static byte[] getStaticFilesPath(String fileName) throws IOException {
        String fileUrl = FilePathParser.class.getResource(pathStatic)  + fileName;
        fileUrl = fileUrl.substring(5);
        byte[] body = Files.readAllBytes(new File(fileUrl).toPath() );
        return body;
    }

    public static byte[] getTemplateFilesPath(String fileName) throws IOException {
        String fileUrl = FilePathParser.class.getResource(pathTemplates)  + fileName;
        fileUrl = fileUrl.substring(5);
        byte[] body = Files.readAllBytes(new File(fileUrl).toPath() );
        return body;
    }
}

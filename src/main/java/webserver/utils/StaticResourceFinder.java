package webserver.utils;

import webserver.domain.ContentType;
import webserver.domain.StatusCodes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class StaticResourceFinder {

    private static final String PATH_STATIC = "/static";
    private static final String PATH_TEMPLATES = "/templates";


    private static Optional<byte[]> makeFileAsByte(String fileUrl) throws IOException {

        fileUrl = fileUrl.substring(5);
        File file = new File(fileUrl);
        if(!file.isFile()){
            return Optional.empty();
        }
        byte[] body = Files.readAllBytes(new File(fileUrl).toPath() );
        return Optional.of(body);

    }

    private static Optional<byte[]> getStaticFilesPath(String fileName) throws IOException {
        String fileUrl = StaticResourceFinder.class.getResource(PATH_STATIC)  + fileName;
        return makeFileAsByte(fileUrl);
    }

    private static Optional<byte[]> getTemplateFilesPath(String fileName) throws IOException {
        String fileUrl = StaticResourceFinder.class.getResource(PATH_TEMPLATES)  + fileName;
        return makeFileAsByte(fileUrl);
    }

    public static Optional<byte[]> staticFileResolver(String fileName) throws IOException {
        Optional<byte[]> fileAsByte = getTemplateFilesPath(fileName);
        if(fileAsByte.isEmpty())
            fileAsByte = getStaticFilesPath(fileName);
        return fileAsByte;
    }

    public static ContentType getExtension(String fileName){
        String[] extStrArr = fileName.split("\\.");
        String extStr = extStrArr[extStrArr.length-1];
        return ContentType.findExtension(extStr);
    }

}

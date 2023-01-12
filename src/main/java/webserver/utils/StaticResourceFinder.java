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
    private static final Integer STATIC_MASK = 1;
    private static final Integer TEMPLATE_MASK = 2;

    private static Optional<byte[]> makeFileAsByte(String fileUrl) throws IOException {

        fileUrl = fileUrl.substring(5);
        File file = new File(fileUrl);
        if(!file.isFile()){
            return Optional.empty();
        }
        byte[] body = Files.readAllBytes(new File(fileUrl).toPath() );
        return Optional.of(body);
    }

    private static Optional<byte[]> getStaticFilesPath(String fileName, int pathType) throws IOException {
        if(pathType == STATIC_MASK){  // dig "/static" directory
            String fileUrl = StaticResourceFinder.class.getResource(PATH_STATIC)  + fileName;
            return makeFileAsByte(fileUrl);
        }
        String fileUrl = StaticResourceFinder.class.getResource(PATH_TEMPLATES)  + fileName; // dig "/templates" directory
        return makeFileAsByte(fileUrl);
    }

    public static Optional<byte[]> staticFileResolver(String fileName) throws IOException {
        Optional<byte[]> fileAsByte = getStaticFilesPath(fileName, STATIC_MASK); //search for the requested resource throughout /statics directory
        if(fileAsByte.isEmpty())
            fileAsByte = getStaticFilesPath(fileName, TEMPLATE_MASK); //search for the requested resource throughout /templates directory
        return fileAsByte;
    }

    public static ContentType getExtension(String fileName){
        String[] extStrArr = fileName.split("\\.");
        String extStr = extStrArr[extStrArr.length-1];
        return ContentType.findExtension(extStr);
    }

}

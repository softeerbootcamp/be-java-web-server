package webserver.utils;

import webserver.Controller.StaticController;
import webserver.domain.StatusCodes;
import webserver.exception.HttpRequestException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticControllerUtil {

    private static final String PATH_STATIC = "/static";
    private static final String PATH_TEMPLATES = "/templates";


    public static byte[] makeFileAsByte(String fileUrl) throws IOException {

        fileUrl = fileUrl.substring(5);
        File file = new File(fileUrl);
        if(!file.isFile()){
            StatusCodes error = StatusCodes.findStatus("NOT_FOUND");
            throw new HttpRequestException(error.getStatusCode(), error.getStatusMsg());
        }
        byte[] body = Files.readAllBytes(new File(fileUrl).toPath() );
        return body;

    }

    public static byte[] getStaticFilesPath(String fileName) throws IOException {
        String fileUrl = StaticControllerUtil.class.getResource(PATH_STATIC)  + fileName;
        return makeFileAsByte(fileUrl);
    }

    public static byte[] getTemplateFilesPath(String fileName) throws IOException {
        String fileUrl = StaticControllerUtil.class.getResource(PATH_TEMPLATES)  + fileName;
        return makeFileAsByte(fileUrl);
    }

    public static byte[] staticFileResolver(String fileName) throws IOException {
        String[] type = fileName.split("\\.");
        byte[] fileAsByte;
        try{
            fileAsByte = getTemplateFilesPath(fileName);
        }catch (HttpRequestException e){
            fileAsByte = getStaticFilesPath(fileName);
        }
        return fileAsByte;
    }
}

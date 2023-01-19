package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtils {

    public static byte[] loadFileFromClasspath(String filePath) throws URISyntaxException, FileNotFoundException {
        URL fileUrl = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (fileUrl == null) {
            throw new FileNotFoundException();
        }
        Path path = Paths.get(fileUrl.toURI());
        byte[] res = new byte[0];
        try{
            res =  Files.readAllBytes(path);
        }catch(IOException e){
            e.printStackTrace();
        }
        return res;

    }
}

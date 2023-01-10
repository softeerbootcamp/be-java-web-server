package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    static final String STATIC_PATH = "src/main/resources/static/";
    static final String TEMPLATES_PATH = "src/main/resources/templates/";

    public byte[] findFilePath(String url) throws IOException {
        logger.debug("> request url : {}", url);

        String substring = url.substring(url.lastIndexOf(".")+1);
        logger.debug(">>> substring : {}", substring);

        if (substring.equals("html") || substring.equals("ico")) {
            return Files.readAllBytes(new File(ViewResolver.TEMPLATES_PATH + url).toPath());
        } else{
            int indexOfRelativePath = url.lastIndexOf("/") + 1;
            String fileName = url.substring(indexOfRelativePath);
            return Files.readAllBytes(new File(ViewResolver.STATIC_PATH + substring+"/" + fileName).toPath());
        }
    }
}

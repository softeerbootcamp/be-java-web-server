package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    static final String STATIC_PATH = "src/main/resources/static/";
    static final String TEMPLATES_PATH = "src/main/resources/templates/";

    public byte[] findFilePath(String url) throws IOException {
        logger.debug(">> request url : {}", url);

        try {
            return Files.readAllBytes(new File(ViewResolver.STATIC_PATH + url).toPath());
        } catch (IOException e) {
            return Files.readAllBytes(new File(ViewResolver.TEMPLATES_PATH + url).toPath());
        }
    }

    public Path findPath(String url) {
        return new File(ViewResolver.STATIC_PATH + url).toPath();


    }
}

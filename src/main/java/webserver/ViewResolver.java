package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class ViewResolver {
    private static final Logger logger = LoggerFactory.getLogger(ViewResolver.class);

    private static final String STATIC_PATH = "src/main/resources/static/";
    private static final String TEMPLATES_PATH = "src/main/resources/templates/";

    public byte[] findFileBytes(Path path) throws IOException {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return Files.readAllBytes(path);
        }
    }

    public Path findFilePath(String url) throws IOException {
        try {
            Path path = Paths.get(STATIC_PATH + url);
            return path.toRealPath(LinkOption.NOFOLLOW_LINKS);
        } catch (IOException e) {
            Path path = Paths.get(TEMPLATES_PATH + url);
            return path.toRealPath(LinkOption.NOFOLLOW_LINKS);
        }
    }
}

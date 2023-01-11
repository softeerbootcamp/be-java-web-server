package webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewResolver {

    private static final String STATIC_PATH = "src/main/resources/static/";
    private static final String TEMPLATES_PATH = "src/main/resources/templates/";

    public byte[] findActualFile(Path path) throws IOException {
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

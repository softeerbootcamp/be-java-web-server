package filesystem;

import enums.ContentType;
import enums.Status;
import io.request.PathParser;
import io.response.FindResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileSystem {

    private final Logger logger = LoggerFactory.getLogger(FileSystem.class);
    private final PathParser pathParser = new PathParser();

    public FindResult findResource(String url) {
        String resourcePath = pathParser.parse(url);
        byte[] resource = readFile(new File(resourcePath));
        if (resourcePath.endsWith("notfound.html")) {
            return new FindResult(Status.NOT_FOUND, ContentType.HTML, resource);
        }
        return new FindResult(Status.OK, ContentType.find(resourcePath), resource);
    }

    private byte[] readFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return new byte[0];
    }
}

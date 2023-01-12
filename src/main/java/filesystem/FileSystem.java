package filesystem;

import io.request.PathParser;
import io.response.FindResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileSystem {

    private static final Logger logger = LoggerFactory.getLogger(FileSystem.class);
    private final PathParser pathParser = new PathParser();

    public FindResult findResource(String url) {
        String resourcePath = pathParser.parse(url);
        byte[] resource = readFile(new File(resourcePath));

        FindResult findResult = new FindResult(resourcePath, resource);
        return findResult;
    }

    private byte[] readFile(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return new byte[0];
        }
    }
}

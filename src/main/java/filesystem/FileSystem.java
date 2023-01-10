package filesystem;

import controller.FacadeController;
import enums.ContentType;
import enums.Status;
import io.request.PathParser;
import io.response.FindResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystem {

    private final Logger logger = LoggerFactory.getLogger(FacadeController.class);
    private final PathParser pathParser = new PathParser();

    public FindResult findResource(String url) {
        FindResult findResult = new FindResult(Status.NOT_FOUND, ContentType.HTML, new byte[0]);
        String resourcePath = pathParser.parse(url);
        if (resourcePath.contains("notfound.html")) {
            return findResult;
        }
        byte[] resource = readFile(new File(resourcePath));
        findResult.update(Status.OK, ContentType.find(resourcePath), resource);
        return findResult;
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

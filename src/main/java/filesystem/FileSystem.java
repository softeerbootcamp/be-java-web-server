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

public class FileSystem {

    private final Logger logger = LoggerFactory.getLogger(FacadeController.class);
    private final PathParser pathParser = new PathParser();

    public FindResult findResource(String url) {
        FindResult findResult = new FindResult();
        String resourcePath = pathParser.parse(url);
        try {
            byte[] resource = Files.readAllBytes(new File(resourcePath).toPath());
            findResult.update(Status.OK, ContentType.find(resourcePath), resource);
        } catch (IOException e) {
            logger.info("resource not found");
        }
        return findResult;
    }
}

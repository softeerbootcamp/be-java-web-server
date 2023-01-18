package reader.fileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Url;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFileReader implements FileReader{

    private static final Logger logger = LoggerFactory.getLogger(StaticFileReader.class);
    private static final String STATIC_ROUTE = "./src/main/resources/static";
    @Override
    public byte[] readFile(Url url) throws IOException {
        byte[] bytes = null;

        bytes = Files.readAllBytes(new File(STATIC_ROUTE + url.getUrl()).toPath());

        return bytes;
    }
}

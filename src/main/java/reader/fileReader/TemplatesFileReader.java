package reader.fileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.Url;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TemplatesFileReader implements FileReader {
    private static final Logger logger = LoggerFactory.getLogger(TemplatesFileReader.class);

    private static final String TEMPLATE_ROUTE = "./src/main/resources/templates";

    @Override
    public byte[] readFile(Url url) throws IOException {
        byte[] bytes = null;

        bytes = Files.readAllBytes(new File(TEMPLATE_ROUTE + url.getUrl()).toPath());

        return bytes;

    }
}

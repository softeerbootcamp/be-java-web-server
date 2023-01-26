package reader.fileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import request.RequestDataType;
import request.Url;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TemplatesFileReader implements FileReader {
    private static final Logger logger = LoggerFactory.getLogger(TemplatesFileReader.class);

    private static final String TEMPLATE_ROUTE = "./src/main/resources/templates";

    @Override
    public byte[] readFile(Url url) throws IOException {
        String urlStr = splitPathVariable(url);
        urlStr = attachHtmlExtension(urlStr);
        byte[] bytes = Files.readAllBytes(new File(TEMPLATE_ROUTE + urlStr).toPath());
        return bytes;

    }

    private String splitPathVariable(Url url) {
        String urlStr = url.getUrl();
        if (url.getRequestDataType().equals(RequestDataType.PATH_VARIABLE)) {
            urlStr = "";
            String[] parts = url.getUrl().split("/");
            for (int i = 0; i < parts.length-1; i++) {
                urlStr += "/" + parts[i];
            }
        }
        return urlStr;
    }

    private String attachHtmlExtension(String url) {
        if (!url.contains(".html")) {
            url += ".html";
        }
        return url;
    }

}

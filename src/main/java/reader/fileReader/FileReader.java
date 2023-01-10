package reader.fileReader;

import util.Url;
import util.UrlType;

public interface FileReader {
    byte[] readFile(Url url);

    static FileReader selectFileReader(Url url) {
        UrlType urlType = url.getUrlType();
        if (urlType.equals(UrlType.TEMPLATES_FILE)) {
            return new TemplatesFileReader();
        } else if (urlType.equals(UrlType.STATIC_FILE)) {
            return new StaticFileReader();
        } else {
            return null;
        }
    }
}

package reader.fileReader;

import util.Url;
import util.RequestDataType;

import java.io.IOException;

public interface FileReader {
    byte[] readFile(Url url) throws IOException;

    static FileReader selectFileReader(RequestDataType requestDataType) {
        switch (requestDataType) {
            case TEMPLATES_FILE:
                return new TemplatesFileReader();
            case STATIC_FILE:
                return new StaticFileReader();
            default:
                return null;
        }

    }
}

package reader.fileReader;

import request.Url;
import request.RequestDataType;

import java.io.IOException;

public interface FileReader {
    byte[] readFile(Url url) throws IOException;


}

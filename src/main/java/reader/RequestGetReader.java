package reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reader.fileReader.FileReader;
import request.HttpRequest;
import util.error.HttpsErrorMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestGetReader implements RequestReader{
    private static final String GET_METHOD_REGEX = "GET (/.*) HTTP/1.1";
    private static final Logger logger = LoggerFactory.getLogger(RequestGetReader.class);



    @Override
    public byte[] readData(HttpRequest httpRequest) {
        return new byte[0];
    }


}

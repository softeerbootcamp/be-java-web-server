package webserver;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.HttpRequestError;
import webserver.exception.NoFileException;
import webserver.utils.RequestUtil;

import static webserver.utils.FilePathParser.*;
import static webserver.utils.RequestUtil.*;
import static webserver.utils.ResponseUtil.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private RequestUtil requestUtil;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public byte[] staticRequestResolver(String fileName) throws IOException, NoFileException {
        String[] type = fileName.split("\\.");
        String typeName = type[type.length - 1];
        if(typeName.equals("html") || typeName.equals("ico"))
            return getTemplateFilesPath(fileName);
        else
            return getStaticFilesPath(fileName);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            Map<String, String> reqMap = parseHttpRequest(in);
            readHttpRequest(reqMap);  //Read Http Request Message
            String fileName = findFilePath(reqMap); //Get an absolute file path for the requested static file
            makeResponse(fileName, out);  //Create Http Response Message

        } catch (IOException e) {
            logger.debug(e.getMessage());
        }
    }

}

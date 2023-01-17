package controller;

import util.HttpStatus;
import view.RequestHeaderMessage;
import view.RequestMessage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public interface Controller {

    void control(RequestMessage requestMessage, OutputStream out);
    default byte[] getBodyFile(String fileURL){
        if (fileURL.contains(".")) {  //파일을 요청 (.html, .js, .css etc..)
            try {
                return Files.readAllBytes(new File(fileURL).toPath());
            } catch (IOException e){
                //logger.debug(e.getMessage());
            }
        }
        return new byte[0];
    }

    default HttpStatus setHttpStatus(byte[] body){
        if (body.length > 0)
            return HttpStatus.Success;
        return HttpStatus.ClientError;
    }

}

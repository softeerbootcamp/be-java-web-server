package controller;

import util.HttpStatus;
import view.RequestHeaderMessage;
import view.RequestMessage;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

public interface Controller {


    void control(RequestMessage requestMessage, OutputStream out);

    default byte[] getBodyFile(String fileURL){
        if (fileURL.contains(".")) {  //파일을 요청 (.html, .js, .css etc..)
            try {
                return Files.readAllBytes(new File(fileURL).toPath());
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return new byte[0];
    }

    default void setLocation(String redirectLink, Map<String,String> headerKV){
        if (!redirectLink.equals("")){
            setHeader(headerKV,"Location", redirectLink);
        }
    }

    default void setHeader(Map<String,String> headerKV, String key, String value){
        headerKV.put(key,value);
    }

}

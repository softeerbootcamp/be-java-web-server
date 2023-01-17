package webserver.utils;

import java.util.Random;

public class HttpCookieUtils {

    private static byte[] byteToHex(byte[] cookieBytes){
        StringBuilder cookie = new StringBuilder();
        for(int i = 0; i < cookieBytes.length; i++){
            cookie.append(String.format("%02x", cookieBytes[i]));
        }
        return cookie.toString().getBytes();
    }

    public static byte[] generateCookie(){
        byte[] cookieBytes = new byte[16];
        new Random().nextBytes(cookieBytes);
        return byteToHex(cookieBytes);
    }
}

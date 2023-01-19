package controller;

import static utils.ByteStringUtil.byteArrayToHex;
import static utils.ByteStringUtil.decodeWithHex;

public class DynamicController {
    // byte 를 받아와서 byte 반환하는 형태가 괜찮은가?
    public static byte[] runDynamicFactory(byte[] body){
        byte[] manualBody;
        String stringBody;
        // 1. body를
        stringBody = byteArrayToHex(body);
        stringBody = stringBody.replace("<li><a href=\"user/login.html\" role=\"button\">로그인</a></li>","");
        manualBody = decodeWithHex(stringBody);
        return manualBody;
    }

}

package controller;

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
    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (final byte b : bytes) {
            sb.append(String.format("%02X", b & 0xff));
        }
        return sb.toString();
    }
    public static byte[] decodeWithHex(String hexStr) {
        int len = hexStr.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexStr.charAt(i), 16) << 4)
                    + Character.digit(hexStr.charAt(i + 1), 16));
        }
        return data;
    }
}

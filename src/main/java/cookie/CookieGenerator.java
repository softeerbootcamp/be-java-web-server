package cookie;

import java.util.HashMap;
import java.util.Map;

public class CookieGenerator {

    Map<String, String> cookie = new HashMap<>();

    public CookieGenerator(String cookieID, String cookieValue) {
        cookie.put(cookieID, cookieValue);
    }

    public CookieGenerator of(String cookieId, String cookieValue) {
        String default1 = "JSESSIONID.3efa22de";
        String default2 = "=node01sa18a9ohrv41p33gl1vk6mqn23.node0";
        return new CookieGenerator(cookieId,cookieValue);
    }

    public Map<String,String> getCookie() {
        return cookie;
    }
}

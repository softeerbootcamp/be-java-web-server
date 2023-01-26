package session;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Random;

public class HttpSessions {
    private String id;
    // user id 와 http session 매
    public static Map<String, HttpSession> httpSessions = Maps.newHashMap();

    public static void addHttpSession(String id) {
        httpSessions.put(id, new HttpSession(getRandStringForSessionId()));
    }
    public static boolean cookieValidationCheck(HttpCookie cookie){
        for(HttpSession session : httpSessions.values()){
            if(session.getSessionId().equals(cookie.getSid())){
                return true;
            }
        }
        return false;
    }

    // todo : 적절한 이름 찾자..
    public static String findSessionById(String userId) {
        return httpSessions.get(userId).getSessionId();
    }
    public static String findUserIdBySid(String sid) {
        for (String userId : httpSessions.keySet()) {
            HttpSession oneUserSession = httpSessions.get(userId);
            if (oneUserSession.getSessionId().equals(sid)) return userId;
        }
        // return 못찾았을때 처리..
        return null;
    }
    public static String getRandStringForSessionId() {
        int leftLimit = 48; // digit '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 12;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            if (randomLimitedInt > 57 && randomLimitedInt < 97) {
                randomLimitedInt = 97; // if it's not a digit or lowercase letter, set to 'a'
            }
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}

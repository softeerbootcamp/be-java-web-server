package view;

import java.util.Random;

public class Session {
    private static final int leftLimit = 48;
    private static final int rightLimit = 122;
    private static final int sessionIdLength = 10;

    String sessionId;
    Session(){
        sessionId = new Random().ints(leftLimit,rightLimit+1)
                .filter(i->(i<=57 || i>=65) && (i<=90 || i>=97))
                .limit(sessionIdLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

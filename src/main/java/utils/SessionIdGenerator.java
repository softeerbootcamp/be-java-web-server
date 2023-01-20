package utils;

import java.security.SecureRandom;
import java.math.BigInteger;

public class SessionIdGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateSessionId() {
        return new BigInteger(130, secureRandom).toString(32);
    }
}

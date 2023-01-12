package Utility;

public class Utility {

    public static boolean isEmailValid(String email) {
        if (!email.contains("@"))
            return false;
        if (email.lastIndexOf("@") != email.indexOf("@"))
            return false;
        return email.substring(email.indexOf("@") + 1).contains(".");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
}

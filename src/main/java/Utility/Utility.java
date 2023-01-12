package Utility;

public class Utility {

    public static boolean isNull(Object target){
        return target == null;
    }

    public static boolean isEmailValid(String email) {
        if(isNull(email))
            return false;
        if (!email.contains("@"))
            return false;
        if (email.lastIndexOf("@") != email.indexOf("@"))
            return false;
        return email.substring(email.indexOf("@") + 1).contains(".");
    }

    public static boolean isPasswordValid(String password) {
        if(isNull(password))
            return false;
        return password.length() >= 6;
    }

    public static boolean isNameValid(String name){
        return name != null;
    }
}

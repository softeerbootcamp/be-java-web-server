package Utility;

import java.util.Objects;

public class UserValidation {

    public static boolean isUserCreationFormValid(String userIdOrNull, String passwordOrNull, String nameOrNull, String emailOrNull){
        return isIdValid(userIdOrNull)
                && isEmailValid(emailOrNull)
                && isPasswordValid(passwordOrNull)
                && isNameValid(nameOrNull);
    }

    public static boolean isIdValid(String userIdOrNull){
        return Objects.isNull(userIdOrNull);
    }

    public static boolean isEmailValid(String emailOrNull) {
        if (Objects.isNull(emailOrNull))
            return false;
        if (!emailOrNull.contains("@"))
            return false;
        if (emailOrNull.lastIndexOf("@") != emailOrNull.indexOf("@"))
            return false;
        return emailOrNull.substring(emailOrNull.indexOf("@") + 1).contains(".");
    }

    public static boolean isPasswordValid(String passwordOrNull) {
        if (Objects.isNull(passwordOrNull))
            return false;
        return passwordOrNull.length() >= 6;
    }

    public static boolean isNameValid(String nameOrNull) {
        return Objects.isNull(nameOrNull);
    }
}

package Utility;

public class PostValidation {
    public static boolean isValid(String userId, String title, String contents) {
        if (userId == null || title == null || contents == null)
            return false;

        return !userId.equals("") && !title.equals("") && !contents.equals("");
    }
}

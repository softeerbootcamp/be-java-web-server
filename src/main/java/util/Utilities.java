package util;

public class Utilities {

    public static boolean checkData(String line) {
        String[] data = line.split("\\?");
        try {
            String temp = data[1];
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }
    }
}

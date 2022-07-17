package todo.application.util;

public class Validation {
    public static boolean isValid(String paramName, String paramValue, int minLetters, int maxLetters) {
        if(paramValue == null) {
            throw new IllegalArgumentException(paramName + " is null");
        }
        else if(paramValue.trim().length() == 0){
            throw new IllegalArgumentException(paramName + " is empty");
        }
        else if(paramValue.trim().length() > maxLetters){
            throw new IllegalArgumentException(paramName + " is too long. Maximum letters allowed is " + maxLetters);
        }
        else if(paramValue.trim().length() < minLetters){
            throw new IllegalArgumentException(paramName + " is too short. Minimum letters allowed is " + minLetters);
        }

        return true;
    }
}

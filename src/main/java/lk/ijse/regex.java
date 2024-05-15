package lk.ijse;

import java.util.regex.Pattern;

public class regex {
    public boolean isPasswordValid(String Password) {
        boolean matches = Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", Password);
        return matches;
    }
    public boolean isEmailValid(String email) {
        boolean matches = Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
        return matches;
    }
    public boolean isIdNumValid(String idNum) {
        boolean matches = Pattern.matches("^([1-9]{1}[0-9]{8}[VvXx])|([1-9]{1}[0-9]{11})$", idNum);
        return matches;
    }
}

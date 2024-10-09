package com.rafalopez.tp3_foto_perfil.utils;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class UtilsValidation {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

package com.rafalopez.inmobiliaria.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    public static String stringToDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(dateStr);
            return dateToString(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

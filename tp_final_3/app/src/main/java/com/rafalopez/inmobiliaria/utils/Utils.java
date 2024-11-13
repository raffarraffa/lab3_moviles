package com.rafalopez.inmobiliaria.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase de utilidad  para la conversion de fechas entre formatos de String y Date
 */
public class Utils {

    /**
     * Convierte un objeto Date a un  String con formato "dd/MM/yyyy"
     *
     * @param date La fecha a convertir
     * @return La fecha convertida como String en formato "dd/MM/yyyy", o  null si la fecha es  null
     */
    public static String dateToString(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * Convierte un String en formato "yyyy-MM-dd'T'HH:mm:ss" a un formato de fecha "dd/MM/yyyy"
     *
     * @param dateStr La fecha en formato String
     * @return fecha convertida como String en formato "dd/MM/yyyy", o  null si ocurre un error al parsear
     */
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

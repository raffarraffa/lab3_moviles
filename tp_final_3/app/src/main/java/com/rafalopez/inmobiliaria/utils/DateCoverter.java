package com.rafalopez.inmobiliaria.utils;



import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.rafalopez.inmobiliaria.AppParams;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class DateCoverter implements JsonDeserializer<Date> {
     private static final String DATE_FORMAT = "dd/MM/yyyy";
    //private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        String dateStr = json.getAsString();
        Log.d(AppParams.TAG, "deserialize() called with: json = [" + json + "], typeOfT = [" + typeOfT + "], context = [" + context + "]");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

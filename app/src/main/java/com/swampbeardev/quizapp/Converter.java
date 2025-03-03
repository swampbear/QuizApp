package com.swampbeardev.quizapp;

import android.net.Uri;

import androidx.room.TypeConverter;

public class Converter {
    @TypeConverter
    public static Uri fromString(String string){
        return string == null ? null : Uri.parse(string);
    }

    @TypeConverter
    public static String fromUri(Uri uri){
        return uri == null ? null : uri.toString();
    }

}

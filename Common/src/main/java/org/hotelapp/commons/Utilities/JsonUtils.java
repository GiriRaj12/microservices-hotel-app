package org.hotelapp.commons.Utilities;

import com.google.gson.Gson;

public class JsonUtils {
    private static Gson gson = new Gson();

    public static <T> T fromJson(String obj, Class<T> cls){
        return gson.fromJson(obj, cls);
    }

    public static <T> String toJson(T obj){
        return gson.toJson(obj);
    }
}

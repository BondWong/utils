package com.copo.utils.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Google Json Tools
 *
 * @author junbanghuang
 * @version 2014-3-13
 */
public final class GsonUtils {

    private GsonUtils() {

    }

    /**
     * @param object
     * @return
     */
    public static String toJson(Object object) {

        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    /**
     * Parse json to map
     *
     * @param json
     * @return
     */
    public static Map<String, String> parse2Map(String json) {

        Type stringStringMap = new TypeToken<Map<String, String>>() {
        }.getType();

        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(json, stringStringMap);

        return map;
    }
}

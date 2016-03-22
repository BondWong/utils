package com.copo.utils.data;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.StdSerializerProvider;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON Tool
 *
 * @author junbang
 * @version 2014-3-13
 */
public final class JsonUtils {

    final static ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    static {
        StdSerializerProvider sp = new StdSerializerProvider();
        // sp.setNullValueSerializer(new NullNullSerializer());
        objectMapper = new ObjectMapper(null, sp, null);
        // objectMapper.setDateFormat(new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private JsonUtils() {

    }

    ;

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * JSON串转换为Java泛型对象，可以是各种类型，此方法最为强大。用法看测试用例。
     *
     * @param <T>
     * @param jsonString
     * @param tr         TypeReference,for example: new TypeReference< List<FamousUser> >(){}
     * @return List对象列表
     */
    @SuppressWarnings("unchecked")
    public static <T> T json2GenericObject(String jsonString,
                                           TypeReference<T> tr) {

        if (jsonString == null || "".equals(jsonString)) {
            return null;
        } else {
            try {
                return (T) objectMapper.readValue(jsonString, tr);
            } catch (Exception e) {
                log.warn("json error:" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Java Object to Json String
     */
    public static String toJson(Object object) {
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("json error:" + e.getMessage());
        }
        return jsonString;

    }

    /**
     * Json String to Java Object
     */
    public static Object json2Object(String jsonString, Class<?> c) {

        if (jsonString == null || "".equals(jsonString)) {
            return "";
        } else {
            try {
                return objectMapper.readValue(jsonString, c);
            } catch (Exception e) {
                log.warn("json error:" + e.getMessage());
            }

        }
        return "";
    }

}

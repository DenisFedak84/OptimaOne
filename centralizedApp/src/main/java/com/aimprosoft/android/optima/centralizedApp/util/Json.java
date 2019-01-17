package com.aimprosoft.android.optima.centralizedApp.util;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Json {

    protected static JSONSerializer serializer;
    protected static JSONDeserializer deserializer;

    static {
        serializer = new JSONSerializer();
        deserializer = new JSONDeserializer();
    }

    public static String inJSON(Object s) {
        return serializer.serialize(s);
    }

    public static String inJSON(String s) {
        return serializer.serialize(s);
    }

    public static <T> T outJSON(String o, Class<T> aClass) {
        return aClass.cast(deserializer.deserialize(o, aClass));
    }

    public static Object outJSON(String o) throws Exception {
        return deserializer.deserialize(o);
    }
}

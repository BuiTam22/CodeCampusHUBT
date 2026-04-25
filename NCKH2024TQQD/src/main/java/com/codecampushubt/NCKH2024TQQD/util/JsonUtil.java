package com.codecampushubt.NCKH2024TQQD.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Parse Java object thanh JSON string (pretty print)
     * Su dung: JsonUtil.toJson(object)
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "Error parsing to JSON: " + e.getMessage();
        }
    }

    /**
     * Parse JSON string thanh Java object
     * Su dung: JsonUtil.fromJson(jsonString, MyClass.class)
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing from JSON: " + e.getMessage(), e);
        }
    }
}

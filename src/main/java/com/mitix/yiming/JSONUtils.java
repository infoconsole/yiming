package com.mitix.yiming;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.TimeZone;

public class JSONUtils {
    private static ObjectMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtils.class);

    private static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            // 设置为中国上海时区
            mapper.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
            mapper.configure(SerializationFeature.FLUSH_AFTER_WRITE_VALUE, true);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
        return mapper;
    }

    /**
     * json转换bean
     *
     * @param json
     * @param beanClass
     * @return
     * @throws RuntimeException
     */
    public static <T> T toBean(String json, Class<T> beanClass) throws RuntimeException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(json);
        }
        try {
            return getMapper().readValue(json, beanClass);
        } catch (Exception e) {
            LOGGER.error("json string is---" + json, e);
            throw new RuntimeException("解析请求异常", e);
        }
    }

    /**
     * json转换List
     *
     * @param json
     * @param beanClass
     * @return
     * @throws RuntimeException
     */
    public static <T> T toBeanList(String json, Class<?> beanClass) throws RuntimeException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(json);
        }
        try {
            JavaType javaType = getCollectionType(ArrayList.class, beanClass);
            return getMapper().readValue(json, javaType);
        } catch (Exception e) {
            LOGGER.error("json string is---" + json, e);
            throw new RuntimeException("解析请求异常", e);
        }
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?> elementClasses) {
        return getMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 对象转换成Json
     *
     * @param value
     * @return
     * @throws RuntimeException
     */
    public static String toBeanString(Object value) throws RuntimeException {
        try {
            return getMapper().writeValueAsString(value);
        } catch (Exception e) {
            LOGGER.error("values to json ", e);
            throw new RuntimeException("解析请求异常", e);
        }

    }
}

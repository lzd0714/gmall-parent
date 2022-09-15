package com.atguigu.gmall.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;

/**
 * @program: gmall-parent
 * @author: LZD
 * @create: 2022-08-29 17:45
 **/

public class Jsons {
    private static ObjectMapper mapper = new ObjectMapper();
    /**
     * 把对象转为json字符串
     * @param object
     */
    public static String toStr(Object object) {
        //jackson
        try {
            String s = mapper.writeValueAsString(object);
            return s;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 将字符串转为指定的对象
     * @param jsonStr
     * @param clz
     * @param <T>
     * @return
     */
    public static<T> T toObj(String jsonStr, Class<T> clz) {
        T t = null;
        try {
            t = mapper.readValue(jsonStr, clz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 把MQ消息内容转成指定对象
     * @param message
     * @param clz
     * @param <T>
     * @return
     */
    public static<T> T  toObj(Message message,
                              Class<T> clz) {

        String json = new String(message.getBody());
        return toObj(json,clz);
    }
}

package code.chat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {

    private ObjectMapper mapper;

    private JsonHelper() {
        mapper = new ObjectMapper();
    }



    public ObjectMapper getMapper() {return mapper;}

    public static String toJson(Object obj) {

        try {
            return new JsonHelper().getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  <T> T FromJson(String jason, Class<T> tClass) {


        try {
            return new JsonHelper().getMapper().readValue(jason, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

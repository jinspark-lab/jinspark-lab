package com.mainlab.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ObjectConvertService {
    ObjectMapper objectMapper = new ObjectMapper();

    public <T> T stringToObj (String content, Class<T> type) {
//            String content = "{ \"status\": 200, \"message\": \"jinspark\" }";
//            MessageModel obj = objectConvertService.stringToObj(content.toString(), MessageModel.class);
//            System.out.println(obj);
        try {
            return objectMapper.readValue(content, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> String objToString(T obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.harsha.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer<T>{

    /**
     * Serializes Java object to JSON.
     * @param object Java object
     * @return serialized JSON
     */
    public String serialize(T object) {
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return json;
    }
    
}

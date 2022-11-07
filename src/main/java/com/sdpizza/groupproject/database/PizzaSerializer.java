package com.sdpizza.groupproject.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdpizza.groupproject.entity.item.Pizza;

import java.io.IOException;

public class PizzaSerializer {
    private PizzaSerializer() {}

    public static String serialize(Pizza pizza) {
        ObjectMapper objMapper = new ObjectMapper();
        String json = null;

        try { json = objMapper.writeValueAsString(pizza); }
        catch (IOException ex) { ex.printStackTrace(); }

        return json;
    }

    public static Pizza deserialize(String json) {
        ObjectMapper objMapper = new ObjectMapper();
        Pizza pizza = null;

        try { pizza = objMapper.readValue(json, Pizza.class); }
        catch (JsonProcessingException ex) { ex.printStackTrace(); }

        return pizza;
    }
}

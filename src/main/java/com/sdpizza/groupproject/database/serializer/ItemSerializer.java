package com.sdpizza.groupproject.database.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sdpizza.groupproject.entity.item.Item;
import com.sdpizza.groupproject.entity.item.Pizza;
import com.sdpizza.groupproject.entity.model.Order;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ItemSerializer extends StdSerializer<Item>{
    /* Should NOT be used */
    @Deprecated
    public ItemSerializer() {
        this(null);
    }

    public ItemSerializer(Class<Item> t) {
        super(t);
    }

    public static String serialize(Item item) {
        ObjectMapper objMapper = new ObjectMapper();
        String json = null;

        try { json = objMapper.writeValueAsString(item); }
        catch (IOException ex) { ex.printStackTrace(); }

        System.out.println(json);
        return json;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Item> T deserialize(String json) {
        ObjectMapper objMapper = new ObjectMapper();
        T item = null;
        /* This could probably be done elegantly by reading documentation,
           but I simply do not give a **** */
        if (json.charAt(0) == '\"') {
            json = json.substring(1);
        }
        json = json.replace("\\", "");
        String[] js = json.split("},");
        for (int i = 0; i < js.length; i++) {
            js[i] = js[i] + "}";
        }
        /* This is scuffed and I hate it but whatever works */
        Class<T> clazz = (Class<T>) resolveClass(js[0]);

        try { item = objMapper.readValue(json, clazz); }
        catch (JsonProcessingException ex) { ex.printStackTrace(); }

        return item;
    }

    private static Class<? extends Item> resolveClass(String json) {
        System.out.println("resolve: " + json);
        try {
            ObjectMapper objMapper = new ObjectMapper();
            JsonNode node = objMapper.readTree(json);
            JsonNode n = node.at("/type");
            String x = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(n);

                switch (node.path("type").asText()) {
                    case "PIZZA":
                        return Pizza.class;
                    case "ITEM":
                        return Item.class;
                    default:
                        break;
                }
        } catch (IOException ex) { ex.printStackTrace(); }

        return null;
    }

    public static String serializeOrder(Order order) {
        ObjectMapper objMapper = new ObjectMapper();
        String json = null;

        try { json = objMapper.writeValueAsString(order); }
        catch (IOException ex) { ex.printStackTrace(); }

        return json;

    }

    @Override
    public void serialize(Item item, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Map<String, Object> fields = item.getJsonFields();
        Pizza pizza = (Pizza) item;
        gen.writeStringField("base", pizza.getBase().toString());
        gen.writeStringField("size", pizza.getSize().toString());
        gen.writeArrayFieldStart("toppings");
        for (Pizza.Topping topping : pizza.getToppings()) {
            gen.writeString(topping.toString());
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}

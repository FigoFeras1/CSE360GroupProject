package com.sdpizza.groupproject.database.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sdpizza.groupproject.entity.item.Item;
import com.sdpizza.groupproject.entity.model.Order;

import java.io.IOException;

public class OrderSerializer extends StdSerializer<Order> {
    public OrderSerializer() {
        this(null);
    }

    public OrderSerializer(Class<Order> t) {
        super(t);
    }

    public static String serialize(Order order) {
        ObjectMapper objMapper = new ObjectMapper();
        String json = null;
        try { json = objMapper.writeValueAsString(order); }
        catch (Exception ex) { ex.printStackTrace(); }

        return json;
    }

    @Override
    public void serialize(Order order, JsonGenerator gen, SerializerProvider provider) throws IOException {
//        gen.writeStartObject();
//        gen.writeFieldName("items");
        gen.writeStartArray();
        for (Item item : order.getItems()) {
            gen.writeObject(item);
//            gen.writeNumberField("quantity", entry.getValue());
        }
        gen.writeEndArray();

//        gen.writeObjectField(order.getItems().toString());
//        gen.writeEndObject();
    }
}
